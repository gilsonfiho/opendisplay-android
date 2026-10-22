import { Coffee } from 'lucide-react'
import { Trans, useTranslation } from 'react-i18next'
import { PageSection } from '@/components/PageSection'
import { Button } from '@/components/ui/button'

export function Support() {
  const { t } = useTranslation('landing')

  return (
    <PageSection id="support">
      <h2 className="max-w-[44rem] text-balance font-heading text-[2rem] leading-[1.05] font-light tracking-[-0.02em] text-foreground sm:text-[2.75rem]">
        {t('support.title')}
      </h2>
      <div className="mt-6 max-w-[60ch] space-y-3 text-sm text-muted-foreground [&_a]:text-accent [&_a]:no-underline [&_a:hover]:underline">
        <p>
          <Trans
            i18nKey="support.p1"
            t={t}
            components={{ profile: <a href="https://github.com/josepacelli" /> }}
          />
        </p>
        <p>{t('support.p2')}</p>
        <p>{t('support.p3')}</p>
      </div>
      <Button
        render={<a href="https://ko-fi.com/Q3D5259MWT" target="_blank" rel="noopener" />}
        nativeButton={false}
        className="mt-6 bg-[#72a4f2] text-white hover:bg-[#72a4f2]/85"
      >
        <Coffee className="size-4" aria-hidden="true" />
        {t('support.cta')}
      </Button>
    </PageSection>
  )
}
