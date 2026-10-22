import { Trans, useTranslation } from 'react-i18next'
import { PageSection } from '@/components/PageSection'
import { SectionHeading } from '@/components/SectionHeading'
import { Accordion, AccordionContent, AccordionItem, AccordionTrigger } from '@/components/ui/accordion'

const RICH_COMPONENTS: Record<number, Record<string, React.ReactElement>> = {
  1: { optin: <a href="https://play.google.com/apps/testing/io.github.josepacelli.opendisplay" />, code: <code /> },
  2: { code: <code /> },
  4: { pr5: <a href="https://github.com/josepacelli/opendisplay-android/pull/5" /> },
}

export function Faq() {
  const { t } = useTranslation('landing')
  const items = t('faq.items', { returnObjects: true }) as { q: string; a: string }[]

  return (
    <PageSection id="faq">
      <SectionHeading title={t('faq.title')} />
      <Accordion className="max-w-3xl">
        {items.map((item, i) => (
          <AccordionItem key={item.q} value={`item-${i}`}>
            <AccordionTrigger className="text-left">{item.q}</AccordionTrigger>
            <AccordionContent>
              {RICH_COMPONENTS[i] ? (
                <Trans i18nKey={`faq.items.${i}.a`} t={t} components={RICH_COMPONENTS[i]} />
              ) : (
                item.a
              )}
            </AccordionContent>
          </AccordionItem>
        ))}
      </Accordion>
    </PageSection>
  )
}
