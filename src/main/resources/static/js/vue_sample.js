new Vue({
    el:"#sample",
    data: {
        title: '',
        detail: '',
        titleErrorMessage: '',
        detailErrorMessage:'',
        checkFlg: true
    },
    methods: {
        checkInput:function (event) {
            if (!this.title) {
                this.titleErrorMessage = "タイトルを入力してください"
                this.checkFlg = false
            }
            if (!this.detail) {
                this.detailErrorMessage= "詳細を入力してください"
                this.checkFlg = false
            }
            // form送信をキャンセルする
            if (!this.checkFlg) {
                event.preventDefault()
            }
        }
    }
})