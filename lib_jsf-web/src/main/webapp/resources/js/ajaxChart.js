function getChart(type, url, elementId, color, title) {
    $.ajax({
        url:url,
        dataType:'json',
        success: function (data) {
            $('#' + elementId).gchart({
                type: type,
                maxValue: Math.max.apply(Math, data.values),
                series:[$.gchart.series(data.values, color)],
                dataLabels:data.labels,
                title:title,
                titleSize: 14,
                axes :[$.gchart.axis('left' , 0, Math.max.apply(Math, data.values)), $.gchart.axis('right' , 0, Math.max.apply(Math, data.values))]
            });
        }

    });
}
