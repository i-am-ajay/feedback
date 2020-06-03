const dimension = {'width':300, 'height':100, 'radius':100};
const cent = {'x':(dimension.width/2 +5), 'y':(dimension.height/2+60)};

const svg = d3.select('.canvas')
                .append('svg')
                .attr('width', dimension.width + 150)
                .attr('height',dimension.height + 150);

const graph = svg.append('g')
                .attr('transform',`translate(${cent.x},${cent.y})`);


const pie = d3.pie()
                .sort(null)
                .value(d => d.value);

// arc path
const archPath = d3.arc()
                    .outerRadius(dimension.radius)
                    .innerRadius(dimension.radius / 2);


// add tweens to you chat.
const arcTweens = d =>{
    const i = d3.interpolate(d.endAngle, d.startAngle);
    // return a function that takes current value and based on this value returns an interpolated value.
    return t =>{
        d.startAngle = i(t);
        return archPath(d);
    }
}

// create legend group
const legendGroup = svg.append('g');
legendGroup.attr('transform',`translate(${dimension.width+40},10)`);

                
// update function
const update = data => {
    // set a scale
    const ordinal = d3.scaleOrdinal(d3['schemeSet3']);
    ordinal.domain(data.map(d=> d.name));

    // create legends
    const legend = d3.legendColor()
                    .shapePadding(15)
                    .scale(ordinal);

    legendGroup.call(legend);
    legendGroup.selectAll('text')
                .attr('fill','Grey');
    
    const paths = graph.selectAll('path').data(pie(data));
    // remove present dom.
    paths.exit().remove();
    // update present dom.
    paths.attr('d',archPath);
 
    // enter new dom.
    paths.enter().append('path')
                .attr('class','arc')
                .attr('stroke','#fff')
                .attr('stroke-width',3)
                .attr('fill',d => ordinal(d.data.name))
                .transition().duration(750)
                    .attrTween("d",arcTweens);

}
// read data

//const angles = pie();