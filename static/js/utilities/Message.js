class Message {

    constructor(type, text) {

        this.text = text;

        this.show = this.show.bind(this);
    }

    show() {
        $('<p class="message">').html(this.text).appendTo("body");
    }
}

export {Message};