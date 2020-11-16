LERP = new Object();
LERP.relatorios = new Object();

$(document).ready(function ()  {

    LERP.relatorios.validaDatas = function (){
        var dataInicio = document.frmRelatorio.dataInicio.value;
        var dataFim = document.frmRelatorio.dataFim.value;

        if((dataInicio == "" || dataInicio == undefined) || (dataFim == "" || dataFim == undefined)){
            return false;
        }
        return true;
    }

    LERP.relatorios.buscarDados = function (){
        if(LERP.relatorios.validaDatas()) {

            var dataInicio = document.frmRelatorio.dataInicio.value;
            var dataFim = document.frmRelatorio.dataFim.value;

            $.ajax({
                type: "GET",
                url: "/LERP/rest/relatorio/buscar",
                data: "dataInicio=" + dataInicio + "&dataFim=" + dataFim,
                success: function (dados) {
                    LERP.relatorios.geraHTML(JSON.parse(dados));
                },
                error: function (info) {
                    LERP.modalAviso("Erro ao buscar dados para relatório: " + info.status + " - " + info.statusText);
                }
            })
        }
    }

    LERP.relatorios.geraHTML= function (dados){
        $("#date").html(LERP.relatorios.pegaDataAtual())
        $("#inicio-periodo-relatorio").html("Inicio do periodo: " + LERP.relatorios.formataData(document.frmRelatorio.dataInicio.value));
        $("#fim-periodo-relatorio").html("Fim do periodo: " + LERP.relatorios.formataData(document.frmRelatorio.dataFim.value));


        var html = "<fieldset>"
        html += "<table style=\"width:100%; border: 1px solid black;\">\n" +
                "<tr>\n" +
                "<th>Exemplar</th>\n" +
                "<th>Categoria</th>\n" +
                "<th>Data Saida</th>\n" +
                "<th>Data devolução</th>\n" +
                "</tr>\n" +
                "</hr>\n";

        for(i=0; i < dados.length; i++){
            html += "<tr>\n" +
                    "<td>"+dados[i].tituloExemplar+"</td>\n" +
                    "<td>"+dados[i].idCategoria+"</td>\n" +
                    "<td>"+dados[i].dataSaida+"</td>\n" +
                    "<td>"+dados[i].dataDev+"</td>\n" +
                    "</tr>\n";
        }
        html += "</table>"
        html += "</fieldset>"
        $("#conteudo-relatorio").html(html)
        LERP.relatorios.abreModalRelatorio();
    }

    LERP.relatorios.abreModalRelatorio = function(){
        var modalVisualizarRelatorio = {
            title: "Visualizar relatório",
            height: 750,
            width: 700,
            buttons:{
                "Download" : function () {
                    LERP.relatorios.geraRelatorio()
                },
                "Fechar": function(){
                    $(this).dialog("close");
                }
            },
            close: function(){}
        }

        $("#imagemGerada").dialog(modalVisualizarRelatorio);
    }

    LERP.relatorios.geraRelatorio = function (){

        html2canvas(document.querySelector("#imagemGerada")).then(canvas => {
            var imgData = canvas.toDataURL('image/png');
            var doc = new jsPDF();
            doc.addImage(imgData, 'PNG', 10, 10);
            doc.save('relatório.pdf');
        });

    }

    LERP.relatorios.formataData = function (data){
        var dataString = data.split("-")
        return (dataString[2] + "/" + dataString[1] + "/" +dataString[0])


    }
    LERP.relatorios.pegaDataAtual = function (){
        date = new Date();
        return date.getDate() + "/" + (date.getMonth().length === 1 ? "0" + (date.getMonth() + 1) : (date.getMonth() + 1)) + "/" + date.getFullYear();
    }

    LERP.relatorios.buscarDadosGrafico = function (){
        if(LERP.relatorios.validaDatas()) {

            var dataInicio = document.frmRelatorio.dataInicio.value;
            var dataFim = document.frmRelatorio.dataFim.value;

            $.ajax({
                type: "GET",
                url: "/LERP/rest/relatorio/buscarGrafico",
                data: "dataInicio=" + dataInicio + "&dataFim=" + dataFim,
                success: function (dados) {
                    LERP.relatorios.geraGrafico(JSON.parse(dados));
                },
                error: function (info) {
                    LERP.modalAviso("Erro ao buscar dados para relatório: " + info.status + " - " + info.statusText);
                }
            })
        }
    }

    LERP.relatorios.geraGrafico = function (dados){
        var ctx = document.getElementById('myChart').getContext('2d');
        var myChart = new Chart(ctx, {
            type: 'pie',
            data: {
                labels: dados.categorias,
                datasets: [{
                    label: 'Categorias emprestadas',
                    data: dados.quantidades,
                    backgroundColor: [
                        'rgba(255, 99, 132, 0.2)',
                        'rgba(54, 162, 235, 0.2)',
                        'rgba(255, 206, 86, 0.2)',
                        'rgba(75, 192, 192, 0.2)',
                        'rgba(153, 102, 255, 0.2)',
                        'rgba(255, 159, 64, 0.2)'
                    ],
                    borderColor: [
                        'rgba(255, 99, 132, 1)',
                        'rgba(54, 162, 235, 1)',
                        'rgba(255, 206, 86, 1)',
                        'rgba(75, 192, 192, 1)',
                        'rgba(153, 102, 255, 1)',
                        'rgba(255, 159, 64, 1)'
                    ],
                    borderWidth: 1
                }]
            },
            options: {
                scales: {
                    yAxes: [{
                        ticks: {
                            beginAtZero: true
                        }
                    }]
                }
            }
        });
    }

})