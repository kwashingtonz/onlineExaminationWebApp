
  const dlabels = [
    'Red',
    'Green',
    'Blue',
  ];

  const ddata = {
    labels: dlabels,
    datasets: [{
      backgroundColor: ['rgb(255, 99, 132)','rgb(55, 162, 132)','rgb(255, 206, 186)'],
      borderColor: 'rgb(255, 255, 255)',
      data: [10, 10, 5],
    }]
  };

  const dconfig = {
    type: 'doughnut',
    data: ddata,
    options: {}
  };

  const dmyChart = new Chart(
    document.getElementById('doughnutChart'),
    dconfig
  );
