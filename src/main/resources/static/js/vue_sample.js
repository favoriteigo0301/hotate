new Vue({
    el:"#sample",
    data: {
        title: '',
        category:'',
        detail: '',
        titleErrorMessage: '',
        categoryErrorMessage:'',
        detailErrorMessage:'',
        outputCategories:[],
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
            if (!this.category) {
                this.categoryErrorMessage= "タグ名を入力してください"
                this.checkFlg = false
            }
            if (!this.checkFlg) {
                // form送信をキャンセルする
                event.preventDefault()
            }
        }, addCategory:function (event) {
            //alert("追加ボタン押されたよ")
            if (this.category) {
                this.outputCategories.push(this.category)
                this.category = ''
            } else {
                this.categoryErrorMessage = "タグ名を入力してください"
                this.checkFlg = false
            }
        }, deleteCategory:function (index) {
            this.outputCategories.splice(index,1)
        }

    }
})