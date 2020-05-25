cadastrarNovoLeitor = function () {
    var leitor = new Object();

    leitor.nome = document.frmAddLeitor.inputNome.value;
    leitor.fone = document.frmAddLeitor.inputFone.value;
    leitor.cpf = document.frmAddLeitor.inputCpf.value;
    leitor.endereco = document.frmAddLeitor.inputEndereco.value;

    if(leitor.nome === "" ||
    leitor.fone === "" ||
    leitor.cpf === "" ||
    leitor.endereco === ""){
        alert("Preencha os campos corretamente!")
    }else{
        $.ajax({
            type: "POST",
            url: "/LERP/rest/leitor/inserir",
            data: JSON.stringify(leitor),

            success: function (msg) {
                LERP.modalAviso(msg);
                $("#addExemplar").trigger("reset");
            },
            error: function (info) {
                LERP.modalAviso("Erro ao cadastrar leitor: "+info.status+" - " + info.statusText);
            }
        })
    }
}