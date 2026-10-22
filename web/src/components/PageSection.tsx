import type { ReactNode } from 'react'
import { cn } from '@/lib/utils'

/** One landing-page section, on the page's vertical rhythm. Content is always visible: nothing
 * waits on scroll position to appear. */
export function PageSection({
  id,
  className,
  children,
}: {
  id: string
  className?: string
  children: ReactNode
}) {
  return (
    <section id={id} className={cn('py-12 sm:py-16', className)}>
      {children}
    </section>
  )
}
