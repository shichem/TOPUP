$(function() {

    

    Morris.Donut({
        element: 'morris-donut-chart',
        data: [{
            label: "Djezzy",
            value: 1500
        }, {
            label: "Mobilis",
            value: 3000
        }, {
            label: "Ooredoo",
            value: 2000
        }],
        resize: true
    });

    Morris.Donut({
        element: 'morris-sold-chart',
        data: [{
            label: "Djezzy DA",
            value: 150000
        }, {
            label: "Mobilis DA",
            value: 300000
        }, {
            label: "Ooredoo DA",
            value: 200000
        }],
        resize: true
    });

    //Flot Pie Chart
    $(function() {
    
        var data = [{
            label: "Sim 1 Djezzy",
            data: 100
        }, {
            label: "Sim 2 Djezzy",
            data: 300
        }, {
            label: "Sim  1 Mobilis",
            data: 900
        }, {
            label: "Sim 1 Ooredoo",
            data: 2000
        }];
    
        var plotObj = $.plot($("#flot-pie-chart"), data, {
            series: {
                pie: {
                    show: true
                }
            },
            grid: {
                hoverable: true
            },
            tooltip: true,
            tooltipOpts: {
                content: "%p.0%, %s", // show percentages, rounding to 2 decimal places
                shifts: {
                    x: 20,
                    y: 0
                },
                defaultTheme: true
            }
        });
    
    });

    Morris.Bar({
        element: 'morris-bar-chart',
        data: [{
            y: 'G 1 ',
            a: 100,
            b: 90,
            c:80
        }, {
            y: 'G 2',
            a: 75,
            b: 65,
            c:80
        }, {
            y: 'G 3',
            a: 50,
            b: 40,
            c:80
        }, {
            y: 'G 4',
            a: 75,
            b: 65,
            c:80
        }, {
            y: 'G 5',
            a: 50,
            b: 40,
            c:80
        }, {
            y: 'G 6',
            a: 75,
            b: 65,
            c:80
        }, {
            y: 'G 7',
            a: 100,
            b: 90,
            c:80
        }],
        xkey: 'y',
        ykeys: ['a', 'b','c'],
        labels: ['Djezzy', 'Mobilis','Ooredoo'],
        hideHover: 'auto',
        resize: true
    });
});
