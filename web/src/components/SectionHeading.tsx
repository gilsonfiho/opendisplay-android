/** A section's heading: a display title and an optional line under it, left-aligned on the page's
 * column like the hero. */
export function SectionHeading({ title, sub }: { title: string; sub?: string }) {
  return (
    <div className="mb-10 max-w-[44rem]">
      <h2 className="text-balance font-heading text-[2rem] leading-[1.05] font-light tracking-[-0.02em] text-foreground sm:text-[2.75rem]">
        {title}
      </h2>
      {sub && <p className="mt-3 text-base leading-relaxed text-muted-foreground">{sub}</p>}
    </div>
  )
}
