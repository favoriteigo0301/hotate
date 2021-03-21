new Vue({
    el:"#sample",
    data: {
        subject: '',
        category:'',
        memo: '',
        userNameErrorMessage:'',
        subjectErrorMessage: '',
        categoryErrorMessage:'',
        memoErrorMessage:'',
        outputCategories:[],
        checkFlg: true
    },
    methods: {
        checkInput:function (event) {
            if (!this.subject) {
                this.subjectErrorMessage = "タイトルを入力してください"
                this.checkFlg = false
            }
            if (!this.memo) {
                this.memoErrorMessage= "詳細を入力してください"
                this.checkFlg = false
            }
            if (!this.checkFlg) {
                // form送信をキャンセルする
                event.preventDefault()
            }
        }, addCategory:function (event) {
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