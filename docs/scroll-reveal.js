(() => {
  const reduceMotion = window.matchMedia("(prefers-reduced-motion: reduce)").matches;
  const sections = document.querySelectorAll(".wrap > section:not(.hero)");
  if (!reduceMotion && sections.length && "IntersectionObserver" in window) {
    sections.forEach((el) => el.classList.add("reveal-pending"));
    const revealIo = new IntersectionObserver(
      (entries) => {
        entries.forEach((entry) => {
          if (entry.isIntersecting) {
            entry.target.classList.add("in-view");
            revealIo.unobserve(entry.target);
          }
        });
      },
      { threshold: 0.12, rootMargin: "0px 0px -8% 0px" }
    );
    sections.forEach((el) => revealIo.observe(el));
  }

  const navLinks = document.querySelectorAll("header.site nav a[href^='#']");
  const sectionByLink = new Map();
  navLinks.forEach((a) => {
    const target = document.getElementById(a.getAttribute("href").slice(1));
    if (target) sectionByLink.set(target, a);
  });
  if (sectionByLink.size && "IntersectionObserver" in window) {
    const navIo = new IntersectionObserver(
      (entries) => {
        entries.forEach((entry) => {
          const link = sectionByLink.get(entry.target);
          if (link) link.classList.toggle("active", entry.isIntersecting);
        });
      },
      { rootMargin: "-45% 0px -50% 0px" }
    );
    sectionByLink.forEach((_, target) => navIo.observe(target));
  }
})();
