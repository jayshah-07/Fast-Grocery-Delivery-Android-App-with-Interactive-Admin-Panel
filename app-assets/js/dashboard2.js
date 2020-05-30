/*=========================================================================================
    File Name: dashboard2.js
    Description: Dashboard 2
    ----------------------------------------------------------------------------------------
    Item Name: Apex - Responsive Admin Theme
    Version: 1.0
    Author: PIXINVENT
    Author URL: http://www.themeforest.net/user/pixinvent
==========================================================================================*/

// chartist chart
// ------------------------------
$(window).on("load", function () {

    // line chart widget 1 configuration Starts
    var widgetlineChart1 = new Chartist.Line('#Widget-line-chart', {
        labels: ["Jan", "Feb", "Mar", "Apr", "May", "June", "July", "Aug"],
        series: [
            [55, 60, 50, 55, 50, 60, 55, 57]
        ]
    }, {
            axisX: {
                showGrid: false,
                showLabel: false,
                offset: 0,
            },
            axisY: {
                showGrid: false,
                low: 50,
                showLabel: false,
                offset: 0,
            },
            fullWidth: true
        });

    // line chart widget 1 configuration Ends

    // line chart widget 2 configuration Starts
    var widgetlineChart2 = new Chartist.Line('#Widget-line-chart2', {
        labels: ["Jan", "Feb", "Mar", "Apr", "May", "June", "July", "Aug"],
        series: [
            [55, 60, 50, 55, 50, 60, 55, 57]
        ]
    }, {
            axisX: {
                showGrid: false,
                showLabel: false,
                offset: 0,
            },
            axisY: {
                showGrid: false,
                low: 50,
                showLabel: false,
                offset: 0,
            },
            fullWidth: true
        });

    // line chart widget 2 configuration Ends

    // line chart widget 3 configuration Starts
    var widgetlineChart3 = new Chartist.Line('#Widget-line-chart3', {
        labels: ["Jan", "Feb", "Mar", "Apr", "May", "June", "July", "Aug"],
        series: [
            [55, 60, 50, 55, 50, 60, 55, 57]
        ]
    }, {
            axisX: {
                showGrid: false,
                showLabel: false,
                offset: 0,
            },
            axisY: {
                showGrid: false,
                low: 50,
                showLabel: false,
                offset: 0,
            },
            fullWidth: true
        });

    // line chart widget 3 configuration Ends

    // line chart widget 4 configuration Starts
    var widgetlineChart4 = new Chartist.Line('#Widget-line-chart4', {
        labels: ["Jan", "Feb", "Mar", "Apr", "May", "June", "July", "Aug"],
        series: [
            [55, 60, 50, 55, 50, 60, 55, 57]
        ]
    }, {
            axisX: {
                showGrid: false,
                showLabel: false,
                offset: 0,
            },
            axisY: {
                showGrid: false,
                low: 50,
                showLabel: false,
                offset: 0,
            },
            fullWidth: true
        });

    // line chart widget 4 configuration Ends

    // Line Chart 1 Starts
    var lineChart1 = new Chartist.Line('#line-chart1', {
        labels: ["Jan", "Feb", "Mar", "Apr", "May", "Jun"],
        series: [
            [65, 105, 145, 105, 145, 185],
            [125, 80, 30, 70, 110, 150],
            [175, 190, 160, 190, 140, 100]
        ]
    }, {
            axisX: {
                showGrid: false,
            },
            axisY: {
                low: 0,
                scaleMinSpace: 50,
            },
            fullWidth: true,
            chartPadding: { top: 0, right: 25, bottom: 0, left: 0 }
        });
    // Line Chart 1 Ends


    // Donut Chart 1 Starts
    var donutChart1 = new Chartist.Pie('#donut-chart1', {
        series: 
            [
                {
                    name: 'done',
                    className: 'ct-done',
                    value: 10
                },
                {
                    name: 'outstanding',
                    className: 'ct-outstanding',
                    value: 3
                }
            ]
        
        // series: [10, 3]
    }, {
            donut: true,
            donutWidth: 3,
            startAngle: 0,
            chartPadding: 25,
            total: 13,
            labelInterpolationFnc: function (value) {
                return '\ue9c9';
            }
        });

    donutChart1.on('draw', function (data) {
        if (data.type === 'label') {
            if (data.index === 0) {
                data.element.attr({
                    dx: data.element.root().width() / 2,
                    dy: (data.element.root().height() + (data.element.height() / 4)) / 2,
                    class: 'ct-label',
                    'font-family': 'feather'
                });
            } else {
                data.element.remove();
            }
        }
    });
    // Donut Chart 1 Ends

    // Donut Chart 2 Starts
    var donutChart2 = new Chartist.Pie('#donut-chart2', {
        series: 
            [
                {
                    "name": "done",
                    "className": "ct-done",
                    "value": 10
                },
                {
                    "name": "outstanding",
                    "className": "ct-outstanding",
                    "value": 3
                }
            ]
        
    }, {
            donut: true,
            donutWidth: 3,
            startAngle: 90,
            chartPadding: 25,
            labelInterpolationFnc: function (value) {
                return '\ue9e7';
            }
        });

    donutChart2.on('draw', function (data) {
        if (data.type === 'label') {
            if (data.index === 0) {
                data.element.attr({
                    dx: data.element.root().width() / 2,
                    dy: (data.element.root().height() + (data.element.height() / 4)) / 2,
                    class: 'ct-label',
                    'font-family': 'feather'
                });
            } else {
                data.element.remove();
            }
        }
    });
    // Donut Chart 2 Ends

    // Donut Chart 3 Starts
    var donutChart3 = new Chartist.Pie('#donut-chart3', {
        series: 
            [
                {
                    "name": "done",
                    "className": "ct-done",
                    "value": 10
                },
                {
                    "name": "outstanding",
                    "className": "ct-outstanding",
                    "value": 3
                }
            ]
        
    }, {
            donut: true,
            donutWidth: 3,
            startAngle: 270,
            chartPadding: 25,
            labelInterpolationFnc: function (value) {
                return '\ue964';
            }
        });

    donutChart3.on('draw', function (data) {
        if (data.type === 'label') {
            if (data.index === 0) {
                data.element.attr({
                    dx: data.element.root().width() / 2,
                    dy: (data.element.root().height() + (data.element.height() / 4)) / 2,
                    class: 'ct-label',
                    'font-family': 'feather'
                });
            } else {
                data.element.remove();
            }
        }
    });
    // Donut Chart 3 Ends

    // Line Chart 2 Starts
    var lineChart2 = new Chartist.Line('#line-chart2', {
        labels: ["Jan", "Feb", "Mar", "Apr", "May", "Jun", "Jul", "Aug", "Sep", "Oct", "Nov", "Dec"],
        series: [
            [160, 150, 140, 120, 75, 35, 45, 65, 100, 145, 160, 180],
            [100, 95, 90, 100, 110, 120, 130, 140, 130, 95, 75, 80]
        ]
    }, {
            axisX: {
                showGrid: false,
            },
            axisY: {
                low: 0,
                scaleMinSpace: 50,
            },
            fullWidth: true,
            chartPadding: { top: 0, right: 25, bottom: 0, left: 0 },
        },
        [
            ['screen and (max-width: 640px) and (min-width: 381px)', {
                axisX: {
                    labelInterpolationFnc: function (value, index) {
                        return index % 2 === 0 ? value : null;
                    }
                }
            }],
            ['screen and (max-width: 380px)', {
                axisX: {
                    labelInterpolationFnc: function (value, index) {
                        return index % 3 === 0 ? value : null;
                    }
                }
            }]
        ]);

    lineChart2.on('draw', function (data) {
        var circleRadius = 6;
        if (data.type === 'point') {
            var circle = new Chartist.Svg('circle', {
                cx: data.x,
                cy: data.y,
                r: circleRadius,
                class: 'ct-point-circle'
            });
            data.element.replace(circle);
        }
    });
    // Line Chart 2 Ends

    // Line Chart 1 Starts
    var WidgetLineChart1 = new Chartist.Line('#Widget-line-chart1', {
        labels: ["Jan", "Feb", "Mar", "Apr", "May"],
        series: [
            [50, 70, 45, 75, 85]
        ]
    }, {
            axisX: {
                showGrid: false,
                showLabel: false,
                offset: 0,
            },
            axisY: {
                showGrid: false,
                low: 50,
                showLabel: false,
                offset: 0,
            },
            fullWidth: true,
            chartPadding: { top: 0, right: 0, bottom: 10, left: 0 }
        });

    WidgetLineChart1.on('created', function (data) {
        var defs = data.svg.elem('defs');
        defs.elem('linearGradient', {
            id: 'widgradient',
            x1: 0,
            y1: 1,
            x2: 0,
            y2: 0
        }).elem('stop', {
            offset: 0,
            'stop-color': 'rgba(132, 60, 247, 1)'
        }).parent().elem('stop', {
            offset: 1,
            'stop-color': 'rgba(56, 184, 242, 1)'
        });
    });
    // Line Chart 1 Ends

    // widget Line Chart 2 Starts
    var WidgetLineChart22 = new Chartist.Line('#Widget-line-chart22', {
        labels: ["1", "2", "3", "4", "5", "6", "7", "8", "9", "10", "11", "12", "13"],
        series: [
            [50, 45, 60, 55, 70, 55, 60, 55, 65, 57, 60, 53, 53]
        ]
    }, {
            axisX: {
                showGrid: true,
                showLabel: false,
                offset: 0,
            },
            axisY: {
                showGrid: false,
                low: 40,
                showLabel: false,
                offset: 0,
            },
            lineSmooth: Chartist.Interpolation.cardinal({
                tension: 0
            }),
            fullWidth: true
        });

    WidgetLineChart22.on('created', function (data) {
        var defs = data.svg.elem('defs');
        defs.elem('linearGradient', {
            id: 'widgradient1',
            x1: 0,
            y1: 1,
            x2: 0,
            y2: 0
        }).elem('stop', {
            offset: 0,
            'stop-color': 'rgba(0, 201, 255,1)'
        }).parent().elem('stop', {
            offset: 1,
            'stop-color': 'rgba(17,228,183, 1)'
        });
    });
    // widget Line Chart 2 Ends

    // Line with Area Chart  Starts
    var lineArea3 = new Chartist.Line('#line-area-chart', {
        labels: [1, 2, 3, 4, 5, 6, 7, 8],
        series: [
            [0, 5, 15, 8, 15, 9, 30, 0],
            [0, 3, 5, 2, 8, 1, 5, 0]
        ]
    }, {
            low: 0,
            showArea: true,
            fullWidth: true,
            onlyInteger: true,
            axisY: {
                low: 0,
                scaleMinSpace: 50,
            },
            axisX: {
                showGrid: false
            }
        });

    lineArea3.on('created', function (data) {
        var defs = data.svg.elem('defs');
        defs.elem('linearGradient', {
            id: 'gradient',
            x1: 0,
            y1: 1,
            x2: 0,
            y2: 0
        }).elem('stop', {
            offset: 0,
            'stop-color': 'rgba(255, 255, 255, 1)'
        }).parent().elem('stop', {
            offset: 1,
            'stop-color': 'rgba(38, 198, 218, 1)'
        });
    });
    lineArea3.on('draw', function (data) {
        var circleRadius = 6;
        if (data.type === 'point') {
            var circle = new Chartist.Svg('circle', {
                cx: data.x,
                cy: data.y,
                r: circleRadius,
                class: 'ct-point-circle'
            });
            data.element.replace(circle);
        }
    });
    // Line with Area Chart  Ends


});