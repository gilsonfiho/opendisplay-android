import { useTranslation } from 'react-i18next'
import { PageSection } from '@/components/PageSection'
import { SectionHeading } from '@/components/SectionHeading'

/** One side of the connection, as the protocol sees it: a ruled column with that end's steps. */
function DiagramColumn({ heading, headingSub, steps }: { heading: string; headingSub: string; steps: string[] }) {
  return (
    <div className="border-t border-border pt-5">
      <p className="font-heading text-lg font-medium text-foreground">
        {heading} <span className="font-sans text-sm font-normal text-muted-foreground">{headingSub}</span>
      </p>
      <ol className="mt-3 space-y-2 font-mono text-[0.8rem] leading-relaxed text-muted-foreground">
        {steps.map((step, i) => (
          <li key={i}>{step}</li>
        ))}
      </ol>
    </div>
  )
}

export function HowItWorks() {
  const { t } = useTranslation('landing')
  const macSteps = t('howItWorks.mac.steps', { returnObjects: true }) as string[]
  const androidSteps = t('howItWorks.android.steps', { returnObjects: true }) as string[]

  return (
    <PageSection id="how-it-works">
      <SectionHeading title={t('howItWorks.title')} sub={t('howItWorks.sub')} />
      <div className="grid gap-x-10 gap-y-8 sm:grid-cols-2">
        <DiagramColumn heading={t('howItWorks.mac.heading')} headingSub={t('howItWorks.mac.headingSub')} steps={macSteps} />
        <DiagramColumn heading={t('howItWorks.android.heading')} headingSub={t('howItWorks.android.headingSub')} steps={androidSteps} />
      </div>
    </PageSection>
  )
}
