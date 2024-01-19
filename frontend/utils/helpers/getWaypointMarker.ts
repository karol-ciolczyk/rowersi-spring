export function getWaypointMarker(): HTMLDivElement {
  const el = document.createElement("div");
  el.style.height = "13px";
  el.style.width = "13px";
  el.style.backgroundColor = "#fff";
  el.style.borderRadius = "50%";
  el.style.border = "2px solid #000";
  return el;
}
