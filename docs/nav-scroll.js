document.querySelectorAll("header.site nav").forEach((nav) => {
  const links = nav.querySelector(".nav-links");
  const start = nav.querySelector(".nav-scroll-start");
  const end = nav.querySelector(".nav-scroll-end");
  if (!links || !start || !end) return;

  const update = () => {
    nav.classList.toggle("can-scroll-start", links.scrollLeft > 0);
    nav.classList.toggle(
      "can-scroll-end",
      links.scrollLeft + links.clientWidth < links.scrollWidth - 1
    );
  };

  start.addEventListener("click", () =>
    links.scrollBy({ left: -links.clientWidth * 0.6, behavior: "smooth" })
  );
  end.addEventListener("click", () =>
    links.scrollBy({ left: links.clientWidth * 0.6, behavior: "smooth" })
  );
  links.addEventListener("scroll", update);
  window.addEventListener("resize", update);
  update();
});
