import {
  Cable,
  Hand,
  MousePointer2,
  PictureInPicture2,
  RefreshCw,
  ScanEye,
  ShieldCheck,
  Sliders,
  Video,
  VenetianMask,
  ZoomIn,
} from 'lucide-react'
import type { ComponentType } from 'react'
import { Trans, useTranslation } from 'react-i18next'
import { PageSection } from '@/components/PageSection'
import { SectionHeading } from '@/components/SectionHeading'

const ICONS: ComponentType<{ className?: string }>[] = [
  Video,
  ScanEye,
  Hand,
  ZoomIn,
  MousePointer2,
  ShieldCheck,
  RefreshCw,
  Sliders,
  Cable,
  PictureInPicture2,
  VenetianMask,
]

export function Features() {
  const { t } = useTranslation('landing')
  const items = t('features.items', { returnObjects: true }) as { title: string; desc: string }[]

  return (
    <PageSection id="features">
      <SectionHeading title={t('features.title')} sub={t('features.sub')} />
      <ul className="grid gap-x-10 gap-y-8 sm:grid-cols-2 lg:grid-cols-3">
        {items.map((item, i) => {
          const Icon = ICONS[i]
          return (
            <li key={item.title} className="border-t border-border pt-5">
              <Icon className="size-5 text-muted-foreground" aria-hidden="true" />
              <h3 className="mt-3 font-heading text-lg font-medium text-foreground">{item.title}</h3>
              <p className="mt-1.5 text-sm leading-relaxed text-muted-foreground">
                <Trans
                  i18nKey={`features.items.${i}.desc`}
                  t={t}
                  components={{ code: <code />, b: <b className="font-medium text-foreground" /> }}
                />
              </p>
            </li>
          )
        })}
      </ul>
    </PageSection>
  )
}
