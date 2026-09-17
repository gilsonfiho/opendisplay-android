(() => {
  const btn = document.getElementById("back-to-top");
  if (!btn) return;
  const threshold = () => window.innerHeight * 0.6;
  const update = () => btn.classList.toggle("visible", window.scrollY > threshold());
  update();
  window.addEventListener("scroll", update, { passive: true });
  window.addEventListener("resize", update);
  btn.addEventListener("click", () => {
    const reduceMotion = window.matchMedia("(prefers-reduced-motion: reduce)").matches;
    window.scrollTo({ top: 0, behavior: reduceMotion ? "auto" : "smooth" });
  });
})();
