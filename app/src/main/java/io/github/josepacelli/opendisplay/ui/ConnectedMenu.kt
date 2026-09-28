package io.github.josepacelli.opendisplay.ui

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.gestures.detectDragGestures
import androidx.compose.foundation.gestures.detectTapGestures
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.widthIn
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.ui.layout.onGloballyPositioned
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.IntSize
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import io.github.josepacelli.opendisplay.R
import io.github.josepacelli.opendisplay.net.ConnectedMenuEdge

private val GLASS_COLOR = Color.Black.copy(alpha = 0.6f)
private val GLASS_BORDER = Color.White.copy(alpha = 0.15f)
private val ACCENT = Color(0xFF6FCF97)
private val DISCONNECT_RED = Color(0xFFE53935)
private val GRIP_SIZE = 40.dp
private val ACTION_SIZE = 52.dp

/**
 * Floating handle shown only while connected — otherwise the app has no chrome at all
 * ([ReceiverScreen]), so this is the only in-app way to disconnect, toggle immersive fullscreen
 * or flip the zoom toggle once video is flowing (issue #151; before this, disconnecting meant
 * reaching for the status bar notification, and both toggles couldn't be changed mid-session
 * since Settings only opens while disconnected).
 *
 * Modeled after a mobile video-call floating control bar (e.g. Microsoft Teams): round,
 * generously sized tap targets ([ACTION_SIZE]) on a glass pill, rather than compact text rows —
 * a first version with small text buttons turned out too fiddly to hit reliably.
 *
 * [ReceiverScreen] mounts/unmounts this composable entirely rather than this function handling
 * its own visibility — it's the one that owns touch activity on the video (to hide the menu
 * while the user is actively interacting) and the master on/off + idle-delay settings, both
 * user-editable in [SettingsDialog]'s General tab.
 *
 * Collapsed, it's a grip dot docked against [edge] (persisted across sessions via
 * [io.github.josepacelli.opendisplay.net.PhoneReceiver.setConnectedMenuEdge] — defaults to the
 * top edge). Tap it to expand the pill into labeled buttons — the zoom/fullscreen toggles,
 * Settings, and Disconnect (red, separated from the rest by a divider so it isn't a stray tap
 * away); tap the grip again, or anywhere outside the pill, to collapse it. The pill lays out
 * along whichever axis its edge is on — a [Column] on the side edges, a [Row] on top/bottom — so
 * it always reads as hugging that edge. Dragging tracks the pointer via raw deltas rather than
 * the handle's own (shifting) position, since which edge it's rendered against — and so its
 * position in the layout — changes live as the drag crosses the screen's midlines;
 * [onEdgeChange] fires on every edge change so the drop position is what gets remembered, not
 * just where the drag ended.
 *
 * @param edge the screen edge to dock against right now.
 * @param onEdgeChange called every time dragging crosses into a different edge's territory.
 * @param zoomEnabled current zoom-pinch toggle state (see [PhoneReceiver.zoomEnabled]).
 * @param onToggleZoom called with the new state when the zoom button is tapped.
 * @param fullscreenEnabled current immersive-fullscreen toggle state (see
 * [PhoneReceiver.immersiveFullscreen]).
 * @param onToggleFullscreen called with the new state when the fullscreen button is tapped.
 * @param onOpenSettings called when Settings is tapped — also collapses the pill, since
 * [SettingsDialog] opens as a full-screen overlay on top of it.
 * @param onDisconnect called when Disconnect is tapped.
 * @param modifier applied to the invisible full-size box the handle drags within — should cover
 * the whole screen so every edge is reachable.
 */
@Composable
fun ConnectedMenu(
    edge: ConnectedMenuEdge,
    onEdgeChange: (ConnectedMenuEdge) -> Unit,
    zoomEnabled: Boolean,
    onToggleZoom: (Boolean) -> Unit,
    fullscreenEnabled: Boolean,
    onToggleFullscreen: (Boolean) -> Unit,
    onOpenSettings: () -> Unit,
    onDisconnect: () -> Unit,
    modifier: Modifier = Modifier,
) {
    var containerSize by remember { mutableStateOf(IntSize.Zero) }
    var dragPosition by remember { mutableStateOf<Offset?>(null) }
    var expanded by remember { mutableStateOf(false) }
    val vertical = edge == ConnectedMenuEdge.START || edge == ConnectedMenuEdge.END

    Box(modifier = modifier.onGloballyPositioned { containerSize = it.size }) {
        if (expanded) {
            Box(
                modifier = Modifier
                    .fillMaxSize()
                    .pointerInput(Unit) { detectTapGestures(onTap = { expanded = false }) },
            )
        }
        Surface(
            color = GLASS_COLOR,
            contentColor = Color.White,
            border = BorderStroke(1.dp, GLASS_BORDER),
            shape = RoundedCornerShape(28.dp),
            modifier = Modifier
                .align(edge.toAlignment())
                .padding(8.dp)
                .pointerInput(Unit) {
                    detectDragGestures { change, dragAmount ->
                        change.consume()
                        val updated = (dragPosition ?: edge.centerOf(containerSize)) + dragAmount
                        dragPosition = updated
                        val newEdge = nearestEdge(updated, containerSize)
                        if (newEdge != edge) onEdgeChange(newEdge)
                    }
                },
        ) {
            val grip = @Composable {
                GripButton(expanded = expanded, onClick = { expanded = !expanded })
            }
            val actions = @Composable {
                if (expanded) {
                    MenuDivider(vertical = vertical)
                    ZoomButton(enabled = zoomEnabled, onClick = { onToggleZoom(!zoomEnabled) })
                    FullscreenButton(enabled = fullscreenEnabled, onClick = { onToggleFullscreen(!fullscreenEnabled) })
                    SettingsButton(onClick = { expanded = false; onOpenSettings() })
                    DisconnectButton(onClick = onDisconnect)
                }
            }
            if (vertical) {
                Column(
                    horizontalAlignment = Alignment.CenterHorizontally,
                    verticalArrangement = Arrangement.spacedBy(10.dp),
                    modifier = Modifier.padding(8.dp),
                ) {
                    grip()
                    actions()
                }
            } else {
                Row(
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.spacedBy(10.dp),
                    modifier = Modifier.padding(8.dp),
                ) {
                    grip()
                    actions()
                }
            }
        }
    }
}

/** Round tap target that expands/collapses [ConnectedMenu] — three dots on a glass circle. */
@Composable
private fun GripButton(expanded: Boolean, onClick: () -> Unit) {
    val label = stringResource(R.string.connected_menu_handle)
    Box(
        contentAlignment = Alignment.Center,
        modifier = Modifier
            .size(GRIP_SIZE)
            .clip(CircleShape)
            .background(Color.White.copy(alpha = if (expanded) 0.18f else 0.1f))
            .clickable(
                interactionSource = remember { MutableInteractionSource() },
                indication = null,
                onClickLabel = label,
                onClick = onClick,
            ),
    ) {
        Row {
            repeat(3) { i ->
                Box(modifier = Modifier.size(4.dp).background(Color.White.copy(alpha = 0.8f), CircleShape))
                if (i != 2) Box(modifier = Modifier.size(3.dp))
            }
        }
    }
}

/** Thin separator between the grip and the action buttons — a line across the axis
 * perpendicular to the pill's layout, same as a [androidx.compose.material3.HorizontalDivider]/
 * `VerticalDivider` would draw, but sized to the pill's compact buttons rather than full width.
 * @param vertical whether the pill stacks its buttons in a [Column] (side edges) rather than a
 * [Row] (top/bottom) — the divider is drawn perpendicular to that stacking axis either way. */
@Composable
private fun MenuDivider(vertical: Boolean) {
    Box(
        modifier = if (vertical) {
            Modifier.width(ACTION_SIZE - 16.dp).height(1.dp)
        } else {
            Modifier.width(1.dp).height(ACTION_SIZE - 16.dp)
        }.background(GLASS_BORDER),
    )
}

/** Round zoom toggle — filled with [ACCENT] while [enabled], glass otherwise. */
@Composable
private fun ZoomButton(enabled: Boolean, onClick: () -> Unit) {
    MenuActionButton(
        label = stringResource(R.string.connected_menu_zoom),
        glyph = "🔍",
        background = if (enabled) ACCENT else Color.White.copy(alpha = 0.12f),
        onClick = onClick,
    )
}

/** Round immersive-fullscreen toggle — filled with [ACCENT] while [enabled], glass otherwise. */
@Composable
private fun FullscreenButton(enabled: Boolean, onClick: () -> Unit) {
    MenuActionButton(
        label = stringResource(R.string.connected_menu_fullscreen),
        glyph = "⛶",
        background = if (enabled) ACCENT else Color.White.copy(alpha = 0.12f),
        onClick = onClick,
    )
}

/** Round Settings button — always glass, since opening Settings isn't a toggle. */
@Composable
private fun SettingsButton(onClick: () -> Unit) {
    MenuActionButton(
        label = stringResource(R.string.settings_title),
        glyph = "⚙",
        background = Color.White.copy(alpha = 0.12f),
        onClick = onClick,
    )
}

/** Round, red Disconnect button — same weight as a video call's hang-up control. */
@Composable
private fun DisconnectButton(onClick: () -> Unit) {
    MenuActionButton(
        label = stringResource(R.string.notification_action_disconnect),
        glyph = "✕",
        background = DISCONNECT_RED,
        glyphBold = true,
        onClick = onClick,
    )
}

/** One action inside the expanded pill — a round glyph button ([ACTION_SIZE]) with its label
 * underneath, since a bare glyph turned out too easy to mistake for a different action (or, for
 * Disconnect, too easy to hit by accident).
 * @param label caption shown below the glyph, and the button's accessibility click label.
 * @param glyph single character/emoji drawn centered in the circle.
 * @param background the circle's fill color — [ACCENT] or [DISCONNECT_RED] when the action is
 * "on" or destructive, translucent glass otherwise.
 * @param glyphBold whether to bold the glyph (Disconnect's ✕ reads better heavier).
 * @param onClick called when tapped. */
@Composable
private fun MenuActionButton(
    label: String,
    glyph: String,
    background: Color,
    glyphBold: Boolean = false,
    onClick: () -> Unit,
) {
    Column(horizontalAlignment = Alignment.CenterHorizontally) {
        Box(
            contentAlignment = Alignment.Center,
            modifier = Modifier
                .size(ACTION_SIZE)
                .clip(CircleShape)
                .background(background)
                .clickable(
                    interactionSource = remember { MutableInteractionSource() },
                    indication = null,
                    onClickLabel = label,
                    onClick = onClick,
                ),
        ) {
            Text(glyph, color = Color.White, fontSize = 20.sp, fontWeight = if (glyphBold) FontWeight.Bold else FontWeight.Normal)
        }
        Text(
            text = label,
            color = Color.White,
            fontSize = 11.sp,
            textAlign = TextAlign.Center,
            maxLines = 2,
            modifier = Modifier.padding(top = 4.dp).widthIn(max = 68.dp),
        )
    }
}

/** @return the edge closest to [pos] within a [size] container — the one [ConnectedMenu] docks
 * against next. */
private fun nearestEdge(pos: Offset, size: IntSize): ConnectedMenuEdge {
    val toStart = pos.x
    val toEnd = size.width - pos.x
    val toTop = pos.y
    val toBottom = size.height - pos.y
    val closest = minOf(toStart, toEnd, toTop, toBottom)
    return when (closest) {
        toStart -> ConnectedMenuEdge.START
        toEnd -> ConnectedMenuEdge.END
        toTop -> ConnectedMenuEdge.TOP
        else -> ConnectedMenuEdge.BOTTOM
    }
}

/** @return this edge's midpoint in a [size] container — the drag's starting reference point. */
private fun ConnectedMenuEdge.centerOf(size: IntSize): Offset = when (this) {
    ConnectedMenuEdge.START -> Offset(0f, size.height / 2f)
    ConnectedMenuEdge.END -> Offset(size.width.toFloat(), size.height / 2f)
    ConnectedMenuEdge.TOP -> Offset(size.width / 2f, 0f)
    ConnectedMenuEdge.BOTTOM -> Offset(size.width / 2f, size.height.toFloat())
}

private fun ConnectedMenuEdge.toAlignment(): Alignment = when (this) {
    ConnectedMenuEdge.START -> Alignment.CenterStart
    ConnectedMenuEdge.END -> Alignment.CenterEnd
    ConnectedMenuEdge.TOP -> Alignment.TopCenter
    ConnectedMenuEdge.BOTTOM -> Alignment.BottomCenter
}
