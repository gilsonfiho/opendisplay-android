(() => {
  const btn = document.getElementById("lang-btn");
  const menu = document.getElementById("lang-menu");
  if (!btn || !menu) return;
  const close = () => {
    menu.hidden = true;
    btn.setAttribute("aria-expanded", "false");
  };
  btn.addEventListener("click", (e) => {
    e.stopPropagation();
    const wasHidden = menu.hidden;
    menu.hidden = !wasHidden;
    btn.setAttribute("aria-expanded", String(wasHidden));
  });
  document.addEventListener("click", (e) => {
    if (!menu.hidden && !menu.contains(e.target) && e.target !== btn) close();
  });
  document.addEventListener("keydown", (e) => {
    if (e.key === "Escape") close();
  });
})();
