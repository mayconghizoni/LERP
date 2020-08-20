LERP.emprestimos = new Object();

$(document).ready(function () {

    LERP.emprestimos.cadastrarEmprestimo = function () {

        var emprestimo = new Object();

        emprestimo.idLeitor = document.frmAddEmprestimo.inputIdLeitor.value;
        emprestimo.idLivro = document.frmAddEmprestimo.inputIdLivro.value;

        if(emprestimo.idLeitor == ""){
            LERP.modalAviso("Preencha o campo com o ID do leitor.")
            document.frmAddEmprestimo.inputIdLeitor.focus()
            return false
        }
        else if(emprestimo.idLivro == ""){
            LERP.modalAviso("Preencha o campo com o ID do livro.")
            document.frmAddEmprestimo.inputIdLivro.focus()
        }
        else{

            $.ajax({
                type: "POST",
                url: "/LERP/rest/emprestimo/inserir",
                data: JSON.stringify(emprestimo),

                success: function (msg) {
                    $("#addEmprestimo").trigger("reset");
                    LERP.modalAviso(msg);

                },
                error: function (info) {
                    LERP.modalAviso("Erro ao realizar empréstimo: "+info.statusText+" - " + info.status);
                }
            })

        }

    }

})