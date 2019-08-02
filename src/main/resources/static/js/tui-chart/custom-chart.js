/******************************************************************************************************************************************************************
 * 파일명 : custom-chart.js
 * 제작자 : kbr
 * 일  자 : 2019-05-10
 * 내  용 : chart script[Admin]
 ******************************************************************************************************************************************************************/

/*======================================================================== Toast Chart UI ========================================================================*/ 

jQuery(document).ready(function(){


/*PaaS-Ta에 사용된 차트*/
/*----------------------------------------------------------------------------------------------------------------------------------------------------------------
//관리자화면 > 등록상품 >  사용현황  > 상품별 현황 상세 : useStatusProdDetail.html
- 월별사용추이_영역차트: useStatusProdDetail
------------------------------------------------------------------------------------------------------------------------------------------------------------------*/

	var container = document.getElementById('useStatusProdDetail');
	var data = {
		categories: ['2018.01', '2018.02', '2018.03', '2018.04', '2018.05', '2018.06', '2018.07', '2018.08', '2018.09', '2018.10', '2018.11', '2018.12',
					'2018.01', '2018.02', '2018.03', '2018.04', '2018.05', '2018.06', '2018.07', '2018.08', '2018.09', '2018.10', '2018.11', '2018.12'],
		series: {
			area: [
				{
					name: 'Effective Load',
					data: [150, 130, 100, 125, 128, 110, 134, 162, 120, 90, 98, 143,
						142, 124, 113, 129, 118, 120, 145, 172, 110, 70, 68, 103]
				}
			],
			line: [
				{
					name: 'Power Usage',
				   data: [150, 130, 100, 125, 128, 110, 134, 162, 120, 90, 98, 143,
						142, 124, 113, 129, 118, 120, 145, 172, 110, 70, 68, 103]
				}
			]
		}
	};
	var options = {
		chart: {
			width: 1000,
			height: 286,
			//title: 'Energy Usage'
		},
		yAxis: {
			title: 'Energy (kWh)',
			min: 0,
			pointOnColumn: true
		},
		xAxis: {
			title: 'Month',
			tickInterval: 'auto'
		},
		series: {
			zoomable: true,
			areaOpacity: 0.3
		},
		tooltip: {
			suffix: 'kWh'
		},
		legend: {
			visible:false
		},
		chartExportMenu : {
			visible: false
		},
		theme: 'newTheme2'
	};

	tui.chart.registerTheme('newTheme2', {
		series: {
			area: {
				colors: ['#0f9e71']
			},
			line: {
				colors: ['#0f9e71']
			}
		}
	});

	var chart = tui.chart.comboChart(container, data, options);
	
	chart.resize({
		width: document.body.clientWidth-420//리사이즈에 대한 스크립트 필요(차트가 속한 부모 div의 너비값)
		//height: document.body.clientHeight/3
	});


	/*	차트 리사이즈 필요*/
	/*
	document.body.onresize = function() {
		chart.resize({
			width: document.body.clientWidth/3,
			height: document.body.clientHeight/3
		});
	};*/



/*Saas에 사용된 차트*/
/*----------------------------------------------------------------------------------------------------------------------------------------------------------------
//관리자화면 > 사용현황 > 공급자 앱현황 목록 : providerAppList-chart.html(등록앱)
- 등록앱_도넛차트: proAppList_donut01
------------------------------------------------------------------------------------------------------------------------------------------------------------------*/
var container = document.getElementById('proAppList_donut01');
var data = {
	categories: ['사용 앱'],
	series: [
		{
			name: '마이크로그리드(MG) 플랫폼 서비스',
			data: 10
		},
		{
			name: '수요자원시장 컨설팅 시스템',
			data: 10
		},
		{
			name: '전기자동차 에너지 발전 시스템',
			data: 10
		},
		{
			name: 'test_data',
			data: 10
		},
		{
			name: 'test_data',
			data: 10
		},
		{
			name: 'test_data',
			data: 10
		},
		{
			name: 'test_data',
			data: 10
		},
		{
			name: 'test_data',
			data: 10
		},
		{
			name: 'test_data',
			data: 10
		},
		{
			name: 'test_data',
			data: 10
		},
		{
			name: 'test_data',
			data: 10
		},
		{
			name: 'test_data',
			data: 10
		},
		{
			name: 'test_data',
			data: 10
		},
		{
			name: 'test_data',
			data: 10
		},
		{
			name: 'test_data',
			data: 10
		},
		{
			name: 'test_data',
			data: 10
		},
		{
			name: 'test_data',
			data: 10
		},
		{
			name: 'test_data',
			data: 10
		},
		{
			name: 'test_data',
			data: 10
		},
		{
			name: 'test_data',
			data: 10
		}
	]
};
var options = {
	chart: {
		width: 280,
		height: 240,
		//title: '사용 앱',
		format: function(value, chartType, areaType, valuetype) {
			if (areaType === 'makingSeriesLabel') { // formatting at series area
				value = value + '%';
			}

			return value;
		}
	},
	series: {
		radiusRange: ['50%', '100%'],
		showLabel: false
	},
	tooltip: {
		suffix: '%'
	},
	legend: {
		visible: false
	},
	chartExportMenu : {
		visible: false
	}
};
var theme = {
	series: {
		/*colors: ['#0d87b8','#f3be14','#bbbcbc'],*/
		label: {
			color: '#fff',
			fontFamily: 'Montserrat',
			fontSize:14,
			fontWeight:'500'
		}
	
	}
};

// For apply theme

tui.chart.registerTheme('myTheme', theme);
options.theme = 'myTheme';

tui.chart.pieChart(container, data, options);








/*----------------------------------------------------------------------------------------------------------------------------------------------------------------
//관리자화면 > 사용현황 > 공급자 앱현황 목록 : providerAppList-chart.html(사용앱)
- 사용앱_도넛차트: proAppList_donut02
------------------------------------------------------------------------------------------------------------------------------------------------------------------*/
var container = document.getElementById('proAppList_donut02');
var data = {
	categories: ['사용 앱'],
	series: [
		{
			name: '마이크로그리드(MG) 플랫폼 서비스',
			data: 10
		},
		{
			name: '수요자원시장 컨설팅 시스템',
			data: 10
		},
		{
			name: '전기자동차 에너지 발전 시스템',
			data: 10
		},
		{
			name: 'test_data',
			data: 10
		},
		{
			name: 'test_data',
			data: 10
		},
		{
			name: 'test_data',
			data: 10
		},
		{
			name: 'test_data',
			data: 10
		},
		{
			name: 'test_data',
			data: 10
		},
		{
			name: 'test_data',
			data: 10
		},
		{
			name: 'test_data',
			data: 10
		},
		{
			name: 'test_data',
			data: 10
		},
		{
			name: 'test_data',
			data: 10
		},
		{
			name: 'test_data',
			data: 10
		},
		{
			name: 'test_data',
			data: 10
		},
		{
			name: 'test_data',
			data: 10
		},
		{
			name: 'test_data',
			data: 10
		},
		{
			name: 'test_data',
			data: 10
		},
		{
			name: 'test_data',
			data: 10
		},
		{
			name: 'test_data',
			data: 10
		},
		{
			name: 'test_data',
			data: 10
		}
	]
};
var options = {
	chart: {
		width: 280,
		height: 240,
		//title: '사용 앱',
		format: function(value, chartType, areaType, valuetype) {
			if (areaType === 'makingSeriesLabel') { // formatting at series area
				value = value + '%';
			}

			return value;
		}
	},
	series: {
		radiusRange: ['50%', '100%'],
		showLabel: false
	},
	tooltip: {
		suffix: '%'
	},
	legend: {
		visible: false
	},
	chartExportMenu : {
		visible: false
	}
};
var theme = {
	series: {
		/*colors: ['#0d87b8','#f3be14','#bbbcbc'],*/
		label: {
			color: '#fff',
			fontFamily: 'Montserrat',
			fontSize:14,
			fontWeight:'500'
		}
	
	}
};

// For apply theme

tui.chart.registerTheme('myTheme', theme);
options.theme = 'myTheme';

tui.chart.pieChart(container, data, options);







/*----------------------------------------------------------------------------------------------------------------------------------------------------------------
//관리자화면 > 사용현황 > 공급자 앱현황 목록 : providerAppDetail-chart.html(사용앱)
- 사용앱_도넛차트: proAppDetail_donut
------------------------------------------------------------------------------------------------------------------------------------------------------------------*/
var container = document.getElementById('proAppDetail_donut');
var data = {
	categories: ['사용 앱'],
	series: [
		{
			name: '마이크로그리드(MG) 플랫폼 서비스',
			data: 10
		},
		{
			name: '수요자원시장 컨설팅 시스템',
			data: 10
		},
		{
			name: '전기자동차 에너지 발전 시스템',
			data: 10
		},
		{
			name: 'test_data',
			data: 10
		},
		{
			name: 'test_data',
			data: 10
		},
		{
			name: 'test_data',
			data: 10
		},
		{
			name: 'test_data',
			data: 10
		},
		{
			name: 'test_data',
			data: 10
		},
		{
			name: 'test_data',
			data: 10
		},
		{
			name: 'test_data',
			data: 10
		},
		{
			name: 'test_data',
			data: 10
		},
		{
			name: 'test_data',
			data: 10
		},
		{
			name: 'test_data',
			data: 10
		},
		{
			name: 'test_data',
			data: 10
		},
		{
			name: 'test_data',
			data: 10
		},
		{
			name: 'test_data',
			data: 10
		},
		{
			name: 'test_data',
			data: 10
		},
		{
			name: 'test_data',
			data: 10
		},
		{
			name: 'test_data',
			data: 10
		},
		{
			name: 'test_data',
			data: 10
		}
	]
};
var options = {
	chart: {
		width: 220,
		height: 224,
		//title: '사용 앱',
		format: function(value, chartType, areaType, valuetype) {
			if (areaType === 'makingSeriesLabel') { // formatting at series area
				value = value + '%';
			}

			return value;
		}
	},
	series: {
		radiusRange: ['50%', '100%'],
		showLabel: false
	},
	tooltip: {
		suffix: '%'
	},
	legend: {
		visible: false
	},
	chartExportMenu : {
		visible: false
	}
};
var theme = {
	series: {
		/*colors: ['#0d87b8','#f3be14','#bbbcbc'],*/
		label: {
			color: '#fff',
			fontFamily: 'Montserrat',
			fontSize:14,
			fontWeight:'500'
		}
	
	}
};

// For apply theme

tui.chart.registerTheme('myTheme', theme);
options.theme = 'myTheme';

tui.chart.pieChart(container, data, options);







/*----------------------------------------------------------------------------------------------------------------------------------------------------------------
//관리자화면 > 사용현황 > 공급자 앱현황 목록 : providerAppDetail-chart.html(사용추이)
- 라인차트: proAppDetail_line
------------------------------------------------------------------------------------------------------------------------------------------------------------------*/
	var container = document.getElementById('proAppDetail_line');
	var data = {
		categories: ['01/01/2016', '02/01/2016', '03/01/2016', '04/01/2016', '05/01/2016', '06/01/2016', '07/01/2016', '08/01/2016', '09/01/2016', '10/01/2016', '11/01/2016', '12/01/2016'],
		series: [
			{
				name: 'Seoul',
				data: [-3.5, -1.1, 4.0, 11.3, 17.5, 21.5, 24.9, 25.2, 20.4, 13.9, 6.6, -0.6]
			},
			{
				name: 'Seattle',
				data: [3.8, 5.6, 7.0, 9.1, 12.4, 15.3, 17.5, 17.8, 15.0, 10.6, 6.4, 3.7]
			},
			{
				name: 'Sydney',
				data: [22.1, 22.0, 20.9, 18.3, 15.2, 12.8, 11.8, 13.0, 15.2, 17.6, 19.4, 21.2]
			},
			{
				name: 'Moskva',
				data: [-10.3, -9.1, -4.1, 4.4, 12.2, 16.3, 18.5, 16.7, 10.9, 4.2, -2.0, -7.5]
			},
			{
				name: 'Jungfrau',
				data: [-13.2, -13.7, -13.1, -10.3, -6.1, -3.2, 0.0, -0.1, -1.8, -4.5, -9.0, -10.9]
			},
			{
				name: 'Jungfrau',
				data: [5.2, 1.3, 6.8, 10.5, 14.4, 19.8, 22.6, 16.3, 15.5, 10.2, 12.6, 17.0]
			}
		]
	};
	var options = {
		chart: {
			width: 440,
			height: 224,
			//title: '24-hr Average Temperature'
		},
		yAxis: {
			//title: 'Temperature (Celsius)',
		},
		xAxis: {
			title: 'Month',
			pointOnColumn: true,
			dateFormat: 'MMM',
			tickInterval: 'auto'
		},
		series: {
			showDot: false,
			zoomable: true
		},
		tooltip: {
			suffix: '°C'
		},
		legend: {
			visible:false
		},
		chartExportMenu : {
			visible: false
		},
		plot: {
			bands: [
				{
					range: ['03/01/2016', '05/01/2016'],
					color: 'gray',
					opacity: 0.2
				}
			],
			lines: [
				{
					value: '03/01/2016',
					color: '#fa2828'
				}
			]
		}
	};
	var theme = {
		series: {
			pointWidth: 1,
			/*colors: [
				'#83b14e', '#458a3f', '#295ba0', '#2a4175', '#289399',
				'#289399', '#617178', '#8a9a9a', '#516f7d', '#dddddd'
			]*/
		}
	};
	// For apply theme
	tui.chart.registerTheme('myTheme_line', theme);
	options.theme = 'myTheme_line';
	var chart = tui.chart.lineChart(container, data, options);







/*----------------------------------------------------------------------------------------------------------------------------------------------------------------
//관리자화면 > 사용현황 > 앱별 사용현황 목록 : usageStatusAppList-chart.html(사용앱)
- 사용앱_도넛차트: usageStAppList_donut
------------------------------------------------------------------------------------------------------------------------------------------------------------------*/
var container = document.getElementById('usageStAppList_donut');
var data = {
	categories: ['사용 앱'],
	series: [
		{
			name: '마이크로그리드(MG) 플랫폼 서비스',
			data: 10
		},
		{
			name: '수요자원시장 컨설팅 시스템',
			data: 10
		},
		{
			name: '전기자동차 에너지 발전 시스템',
			data: 10
		},
		{
			name: 'test_data',
			data: 10
		},
		{
			name: 'test_data',
			data: 10
		},
		{
			name: 'test_data',
			data: 10
		},
		{
			name: 'test_data',
			data: 10
		},
		{
			name: 'test_data',
			data: 10
		},
		{
			name: 'test_data',
			data: 10
		},
		{
			name: 'test_data',
			data: 10
		},
		{
			name: 'test_data',
			data: 10
		},
		{
			name: 'test_data',
			data: 10
		},
		{
			name: 'test_data',
			data: 10
		},
		{
			name: 'test_data',
			data: 10
		},
		{
			name: 'test_data',
			data: 10
		},
		{
			name: 'test_data',
			data: 10
		},
		{
			name: 'test_data',
			data: 10
		},
		{
			name: 'test_data',
			data: 10
		},
		{
			name: 'test_data',
			data: 10
		},
		{
			name: 'test_data',
			data: 10
		}
	]
};
var options = {
	chart: {
		width: 280,
		height: 240,
		//title: '사용 앱',
		format: function(value, chartType, areaType, valuetype) {
			if (areaType === 'makingSeriesLabel') { // formatting at series area
				value = value + '%';
			}

			return value;
		}
	},
	series: {
		radiusRange: ['50%', '100%'],
		showLabel: false
	},
	tooltip: {
		suffix: '%'
	},
	legend: {
		visible: false
	},
	chartExportMenu : {
		visible: false
	}
};
var theme = {
	series: {
		/*colors: ['#0d87b8','#f3be14','#bbbcbc'],*/
		label: {
			color: '#fff',
			fontFamily: 'Montserrat',
			fontSize:14,
			fontWeight:'500'
		}
	
	}
};

// For apply theme

tui.chart.registerTheme('myTheme', theme);
options.theme = 'myTheme';

tui.chart.pieChart(container, data, options);







/*----------------------------------------------------------------------------------------------------------------------------------------------------------------
//관리자화면 > 사용현황 > 앱별 사용현황 목록 : usageStatusAppList-chart.html(사용추이)
- 사용추이_라인차트: usageStAppList_line
------------------------------------------------------------------------------------------------------------------------------------------------------------------*/
	var container = document.getElementById('usageStAppList_line');
	var data = {
		categories: ['01/01/2016', '02/01/2016', '03/01/2016', '04/01/2016', '05/01/2016', '06/01/2016', '07/01/2016', '08/01/2016', '09/01/2016', '10/01/2016', '11/01/2016', '12/01/2016'],
		series: [
			{
				name: 'Seoul',
				data: [-3.5, -1.1, 4.0, 11.3, 17.5, 21.5, 24.9, 25.2, 20.4, 13.9, 6.6, -0.6]
			},
			{
				name: 'Seattle',
				data: [3.8, 5.6, 7.0, 9.1, 12.4, 15.3, 17.5, 17.8, 15.0, 10.6, 6.4, 3.7]
			},
			{
				name: 'Sydney',
				data: [22.1, 22.0, 20.9, 18.3, 15.2, 12.8, 11.8, 13.0, 15.2, 17.6, 19.4, 21.2]
			},
			{
				name: 'Moskva',
				data: [-10.3, -9.1, -4.1, 4.4, 12.2, 16.3, 18.5, 16.7, 10.9, 4.2, -2.0, -7.5]
			},
			{
				name: 'Jungfrau',
				data: [-13.2, -13.7, -13.1, -10.3, -6.1, -3.2, 0.0, -0.1, -1.8, -4.5, -9.0, -10.9]
			},
			{
				name: 'Jungfrau',
				data: [5.2, 1.3, 6.8, 10.5, 14.4, 19.8, 22.6, 16.3, 15.5, 10.2, 12.6, 17.0]
			}
		]
	};
	var options = {
		chart: {
			width: 700,
			height: 250,
			//title: '24-hr Average Temperature'
		},
		yAxis: {
			//title: 'Temperature (Celsius)',
		},
		xAxis: {
			title: 'Month',
			pointOnColumn: true,
			dateFormat: 'MMM',
			tickInterval: 'auto'
		},
		series: {
			showDot: false,
			zoomable: true
		},
		tooltip: {
			suffix: '°C'
		},
		legend: {
			visible:false
		},
		chartExportMenu : {
			visible: false
		},
		plot: {
			bands: [
				{
					range: ['03/01/2016', '05/01/2016'],
					color: 'gray',
					opacity: 0.2
				}
			],
			lines: [
				{
					value: '03/01/2016',
					color: '#fa2828'
				}
			]
		}
	};
	var theme = {
		series: {
			pointWidth: 1,
			/*colors: [
				'#83b14e', '#458a3f', '#295ba0', '#2a4175', '#289399',
				'#289399', '#617178', '#8a9a9a', '#516f7d', '#dddddd'
			]*/
		}
	};
	// For apply theme
	tui.chart.registerTheme('myTheme_line', theme);
	options.theme = 'myTheme_line';
	var chart = tui.chart.lineChart(container, data, options);






/*----------------------------------------------------------------------------------------------------------------------------------------------------------------
//관리자화면 > 사용현황 > 사용자별 앱 사용현황 목록 : usageStatusAppDetail-chart.html(사용자 수)
- 월별 사용추이_영역차트: usageStAppDetail_bar01
------------------------------------------------------------------------------------------------------------------------------------------------------------------*/
var container = document.getElementById('usageStAppDetail_bar01');
var data = {
    categories: ['사용자 수', '남은 사용자 수'],//없으면 마우스오버 툴팁 생성안됨
    series: [
        {name: '사용자 수', data: [50]},
        {name: '남은 사용자 수', data: [50]}
    ]
};
var options = {
    chart: {
        width: 360,
        height: 45,
        format: '1,000'
    },
    yAxis: {
		pointOnColumn :  false ,
        showLabel :  false
    },
    xAxis: {
        pointOnColumn :  false ,
        showLabel :  false,
		min: 0,
        max: 100,
        suffix: '%'
    },
     series: {
		 stackType: 'normal',
         showLabel: false
     },
    legend: {
        visible: false
    },
    chartExportMenu : {
        visible: false
    } ,
    usageStatistics : false
};
var theme = {
    series: {
        colors: ['#f3be14','#ebebeb'] 
    }
};

// For apply theme

	tui.chart.registerTheme('myTheme_bar01', theme);
	options.theme = 'myTheme_bar01';

	tui.chart.barChart(container, data, options);




/*----------------------------------------------------------------------------------------------------------------------------------------------------------------
//관리자화면 > 사용현황 > 사용자별 앱 사용현황 목록 : usageStatusAppDetail-chart.html(사용앱 수)
- 월별 사용추이_영역차트: usageStAppDetail_bar02
------------------------------------------------------------------------------------------------------------------------------------------------------------------*/
var container = document.getElementById('usageStAppDetail_bar02');
var data = {
    categories: ['사용앱 수', '남은 사용앱 수'],//없으면 마우스오버 툴팁 생성안됨
    series: [
        {name: '사용앱 수', data: [30]},
        {name: '남은 사용앱 수', data: [70]}
    ]
};
var options = {
    chart: {
        width: 360,
        height: 45,
        format: '1,000'
    },
    yAxis: {
		pointOnColumn :  false ,
        showLabel :  false
    },
    xAxis: {
        pointOnColumn :  false ,
        showLabel :  false,
		min: 0,
        max: 100,
        suffix: '%'
    },
     series: {
		 stackType: 'normal',
         showLabel: false
     },
    legend: {
        visible: false
    },
    chartExportMenu : {
        visible: false
    } ,
    usageStatistics : false
};
var theme = {
    series: {
        colors: ['#ff6384','#ebebeb'] 
    }
};

// For apply theme

 tui.chart.registerTheme('myTheme_bar02', theme);
 options.theme = 'myTheme_bar02';

tui.chart.barChart(container, data, options);






/*----------------------------------------------------------------------------------------------------------------------------------------------------------------
//관리자화면 > 사용현황 >  앱별 사용현황 상세 : usageStatusAppDetail-chart.html
- 월별사용추이_영역차트: usageStAppDetail_area
------------------------------------------------------------------------------------------------------------------------------------------------------------------*/

	var container = document.getElementById('usageStAppDetail_area');
	var data = {
		categories: ['2018.01', '2018.02', '2018.03', '2018.04', '2018.05', '2018.06', '2018.07', '2018.08', '2018.09', '2018.10', '2018.11', '2018.12',
					'2018.01', '2018.02', '2018.03', '2018.04', '2018.05', '2018.06', '2018.07', '2018.08', '2018.09', '2018.10', '2018.11', '2018.12'],
		series: {
			area: [
				{
					name: 'Effective Load',
					data: [150, 130, 100, 125, 128, 110, 134, 162, 120, 90, 98, 143,
						142, 124, 113, 129, 118, 120, 145, 172, 110, 70, 68, 103]
				}
			],
			line: [
				{
					name: 'Power Usage',
				   data: [150, 130, 100, 125, 128, 110, 134, 162, 120, 90, 98, 143,
						142, 124, 113, 129, 118, 120, 145, 172, 110, 70, 68, 103]
				}
			]
		}
	};
	var options = {
		chart: {
			width: 1000,
			height: 286,
			//title: 'Energy Usage'
		},
		yAxis: {
			title: 'Energy (kWh)',
			min: 0,
			pointOnColumn: true
		},
		xAxis: {
			title: 'Month',
			tickInterval: 'auto'
		},
		series: {
			zoomable: true,
			areaOpacity: 0.3
		},
		tooltip: {
			suffix: 'kWh'
		},
		legend: {
			visible:false
		},
		chartExportMenu : {
			visible: false
		},
		theme: 'newTheme2'
	};

	tui.chart.registerTheme('newTheme2', {
		series: {
			area: {
				colors: ['#0f9e71']
			},
			line: {
				colors: ['#0f9e71']
			}
		}
	});

	var chart = tui.chart.comboChart(container, data, options);
	
	chart.resize({
		width: document.body.clientWidth-420//리사이즈에 대한 스크립트 필요(차트가 속한 부모 div의 너비값)
		//height: document.body.clientHeight/3
	});


	/*	차트 리사이즈 필요*/
	/*
	document.body.onresize = function() {
		chart.resize({
			width: document.body.clientWidth/3,
			height: document.body.clientHeight/3
		});
	};*/






/*----------------------------------------------------------------------------------------------------------------------------------------------------------------
//관리자화면 > 사용현황 > 사용자별 앱 사용현황 목록 : usageStatusUserList-chart.html(사용앱)
- 사용앱_도넛차트: usageStUserList_donut
------------------------------------------------------------------------------------------------------------------------------------------------------------------*/
var container = document.getElementById('usageStUserList_donut');
var data = {
	categories: ['사용 앱'],
	series: [
		{
			name: '마이크로그리드(MG) 플랫폼 서비스',
			data: 10
		},
		{
			name: '수요자원시장 컨설팅 시스템',
			data: 10
		},
		{
			name: '전기자동차 에너지 발전 시스템',
			data: 10
		},
		{
			name: 'test_data',
			data: 10
		},
		{
			name: 'test_data',
			data: 10
		},
		{
			name: 'test_data',
			data: 10
		},
		{
			name: 'test_data',
			data: 10
		},
		{
			name: 'test_data',
			data: 10
		},
		{
			name: 'test_data',
			data: 10
		},
		{
			name: 'test_data',
			data: 10
		},
		{
			name: 'test_data',
			data: 10
		},
		{
			name: 'test_data',
			data: 10
		},
		{
			name: 'test_data',
			data: 10
		},
		{
			name: 'test_data',
			data: 10
		},
		{
			name: 'test_data',
			data: 10
		},
		{
			name: 'test_data',
			data: 10
		},
		{
			name: 'test_data',
			data: 10
		},
		{
			name: 'test_data',
			data: 10
		},
		{
			name: 'test_data',
			data: 10
		},
		{
			name: 'test_data',
			data: 10
		}
	]
};
var options = {
	chart: {
		width: 280,
		height: 240,
		//title: '사용 앱',
		format: function(value, chartType, areaType, valuetype) {
			if (areaType === 'makingSeriesLabel') { // formatting at series area
				value = value + '%';
			}

			return value;
		}
	},
	series: {
		radiusRange: ['50%', '100%'],
		showLabel: false
	},
	tooltip: {
		suffix: '%'
	},
	legend: {
		visible: false
	},
	chartExportMenu : {
		visible: false
	}
};
var theme = {
	series: {
		/*colors: ['#0d87b8','#f3be14','#bbbcbc'],*/
		label: {
			color: '#fff',
			fontFamily: 'Montserrat',
			fontSize:14,
			fontWeight:'500'
		}
	
	}
};

// For apply theme

tui.chart.registerTheme('myTheme', theme);
options.theme = 'myTheme';

tui.chart.pieChart(container, data, options);







/*----------------------------------------------------------------------------------------------------------------------------------------------------------------
//관리자화면 > 사용현황 > 사용자별 앱 사용현황 목록 : usageStatusUserList-chart.html(사용추이)
- 사용추이_라인차트: usageStUserList_line
------------------------------------------------------------------------------------------------------------------------------------------------------------------*/
	var container = document.getElementById('usageStUserList_line');
	var data = {
		categories: ['01/01/2016', '02/01/2016', '03/01/2016', '04/01/2016', '05/01/2016', '06/01/2016', '07/01/2016', '08/01/2016', '09/01/2016', '10/01/2016', '11/01/2016', '12/01/2016'],
		series: [
			{
				name: 'Seoul',
				data: [-3.5, -1.1, 4.0, 11.3, 17.5, 21.5, 24.9, 25.2, 20.4, 13.9, 6.6, -0.6]
			},
			{
				name: 'Seattle',
				data: [3.8, 5.6, 7.0, 9.1, 12.4, 15.3, 17.5, 17.8, 15.0, 10.6, 6.4, 3.7]
			},
			{
				name: 'Sydney',
				data: [22.1, 22.0, 20.9, 18.3, 15.2, 12.8, 11.8, 13.0, 15.2, 17.6, 19.4, 21.2]
			},
			{
				name: 'Moskva',
				data: [-10.3, -9.1, -4.1, 4.4, 12.2, 16.3, 18.5, 16.7, 10.9, 4.2, -2.0, -7.5]
			},
			{
				name: 'Jungfrau',
				data: [-13.2, -13.7, -13.1, -10.3, -6.1, -3.2, 0.0, -0.1, -1.8, -4.5, -9.0, -10.9]
			},
			{
				name: 'Jungfrau',
				data: [5.2, 1.3, 6.8, 10.5, 14.4, 19.8, 22.6, 16.3, 15.5, 10.2, 12.6, 17.0]
			}
		]
	};
	var options = {
		chart: {
			width: 700,
			height: 250,
			//title: '24-hr Average Temperature'
		},
		yAxis: {
			//title: 'Temperature (Celsius)',
		},
		xAxis: {
			title: 'Month',
			pointOnColumn: true,
			dateFormat: 'MMM',
			tickInterval: 'auto'
		},
		series: {
			showDot: false,
			zoomable: true
		},
		tooltip: {
			suffix: '°C'
		},
		legend: {
			visible:false
		},
		chartExportMenu : {
			visible: false
		},
		plot: {
			bands: [
				{
					range: ['03/01/2016', '05/01/2016'],
					color: 'gray',
					opacity: 0.2
				}
			],
			lines: [
				{
					value: '03/01/2016',
					color: '#fa2828'
				}
			]
		}
	};
	var theme = {
		series: {
			pointWidth: 1,
			/*colors: [
				'#83b14e', '#458a3f', '#295ba0', '#2a4175', '#289399',
				'#289399', '#617178', '#8a9a9a', '#516f7d', '#dddddd'
			]*/
		}
	};
	// For apply theme
	tui.chart.registerTheme('myTheme_line', theme);
	options.theme = 'myTheme_line';
	var chart = tui.chart.lineChart(container, data, options);







/*----------------------------------------------------------------------------------------------------------------------------------------------------------------
//관리자화면 > 사용현황 > 사용자별 앱 사용현황 목록 : usageStatusUserDetail-chart.html(월별 사용추이)
- 월별 사용추이_영역차트: usageStUserDetail_area
------------------------------------------------------------------------------------------------------------------------------------------------------------------*/

	var container = document.getElementById('usageStUserDetail_area');
	var data = {
		categories: ['2018.01', '2018.02', '2018.03', '2018.04', '2018.05', '2018.06', '2018.07', '2018.08', '2018.09', '2018.10', '2018.11', '2018.12',
					'2018.01', '2018.02', '2018.03', '2018.04', '2018.05', '2018.06', '2018.07', '2018.08', '2018.09', '2018.10', '2018.11', '2018.12'],
		series: {
			area: [
				{
					name: 'Effective Load',
					data: [150, 130, 100, 125, 128, 110, 134, 162, 120, 90, 98, 143,
						142, 124, 113, 129, 118, 120, 145, 172, 110, 70, 68, 103]
				}
			],
			line: [
				{
					name: 'Power Usage',
				   data: [150, 130, 100, 125, 128, 110, 134, 162, 120, 90, 98, 143,
						142, 124, 113, 129, 118, 120, 145, 172, 110, 70, 68, 103]
				}
			]
		}
	};
	var options = {
		chart: {
			width: 1140,
			height: 228,
			//title: 'Energy Usage'
		},
		yAxis: {
			//title: 'Energy (kWh)',
			min: 0,
			pointOnColumn: true
		},
		xAxis: {
			title: 'Month',
			tickInterval: 'auto'
		},
		series: {
			zoomable: true,
			areaOpacity: 0.3
		},
		tooltip: {
			suffix: 'kWh'
		},
		legend: {
			visible:false
		},
		chartExportMenu : {
			visible: false
		},
		theme: 'newTheme'
	};

	tui.chart.registerTheme('newTheme', {
		series: {
			area: {
				colors: ['#0f9e71']
			},
			line: {
				colors: ['#0f9e71']
			}
		}
	});

	var chart = tui.chart.comboChart(container, data, options);


	/*	차트 리사이즈 필요*/
	/*
	document.body.onresize = function() {
		chart.resize({
			width: document.body.clientWidth/3,
			height: document.body.clientHeight/3
		});
	};*/



});

