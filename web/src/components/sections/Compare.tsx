import { Check, X } from 'lucide-react'
import { useTranslation } from 'react-i18next'
import { PageSection } from '@/components/PageSection'
import { SectionHeading } from '@/components/SectionHeading'
import { Table, TableBody, TableCell, TableHead, TableHeader, TableRow } from '@/components/ui/table'

type Row = { label: string; us: string; duet: string; spacedesk: string }

/** Whether each cell is a point in that product's favour, per row of `compare.rows` (price,
 * open source, account required, same protocol, central server) and column (us, Duet,
 * Spacedesk). A fact about the products, not the wording, so it lives here instead of in the
 * seven locale files. */
const FAVOURABLE: [boolean, boolean, boolean][] = [
  [true, false, true],
  [true, false, false],
  [true, false, true],
  [true, false, false],
  [true, false, true],
]

function Cell({ value, positive }: { value: string; positive: boolean }) {
  return (
    <TableCell className={positive ? 'text-foreground' : 'text-muted-foreground'}>
      <span className="inline-flex items-center gap-1.5">
        {positive ? (
          <Check className="size-4 text-accent" aria-hidden="true" />
        ) : (
          <X className="size-4 text-muted-foreground/60" aria-hidden="true" />
        )}
        {value}
      </span>
    </TableCell>
  )
}

export function Compare() {
  const { t } = useTranslation('landing')
  const headers = t('compare.headers', { returnObjects: true }) as string[]
  const rows = t('compare.rows', { returnObjects: true }) as Row[]

  return (
    <PageSection id="compare">
      <SectionHeading title={t('compare.title')} sub={t('compare.sub')} />
      <div className="overflow-x-auto rounded-lg border border-border">
        <Table>
          <TableHeader>
            <TableRow>
              {headers.map((h) => (
                <TableHead key={h}>{h}</TableHead>
              ))}
            </TableRow>
          </TableHeader>
          <TableBody>
            {rows.map((row, i) => (
              <TableRow key={row.label}>
                <TableCell className="font-medium text-foreground">{row.label}</TableCell>
                <Cell value={row.us} positive={FAVOURABLE[i][0]} />
                <Cell value={row.duet} positive={FAVOURABLE[i][1]} />
                <Cell value={row.spacedesk} positive={FAVOURABLE[i][2]} />
              </TableRow>
            ))}
          </TableBody>
        </Table>
      </div>
    </PageSection>
  )
}
