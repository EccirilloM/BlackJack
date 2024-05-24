import { Injectable } from '@angular/core';
import * as d3 from 'd3';
import d3Tip from 'd3-tip';

@Injectable({
  providedIn: 'root'
})
export class ChartService {

  constructor() { }

  createPieChart(container: HTMLElement, data: { label: string; value: number }[], width: number = 400, height: number = 400): void {
    const radius = Math.min(width, height) / 2;
    const color = d3.scaleOrdinal(d3.schemeCategory10);

    const svg = d3.select(container).append('svg')
      .attr('width', width)
      .attr('height', height)
      .append('g')
      .attr('transform', `translate(${width / 2}, ${height / 2})`);

    const pie = d3.pie<{ label: string, value: number }>().value(d => d.value);
    const arcPath = d3.arc<d3.PieArcDatum<{ label: string; value: number }>>()
      .outerRadius(radius - 10)
      .innerRadius(0);

    const arcs = svg.selectAll('.arc')
      .data(pie(data))
      .enter().append('g')
      .attr('class', 'arc');

    arcs.append('path')
      .attr('fill', (d, i) => color(i.toString()))
      .transition()
      .duration(750)
      .attrTween("d", function (d) {
        const i = d3.interpolate({ startAngle: 0, endAngle: 0 }, d);
        return function (t) {
          const interpolated = i(t); // Risultato dell'interpolazione
          return arcPath(interpolated) || ""; // Assicura di restituire sempre una stringa
        };
      });



    arcs.append('text')
      .attr("transform", d => {
        const [x, y] = arcPath.centroid(d);
        return `translate(${x}, ${y})`;
      })
      .attr("text-anchor", "middle")
      .text(d => `${d.data.label}: ${d.data.value}`)
      .style("fill", "black")
      .style("font-weight", "bold")
      .style("font-size", "14px");
  }
}


