class MapView {
    
    constructor(canvas, spriteSrc) {
    	
        this.c = canvas;

        this.datas = [[]];
        this.height = this.datas.length;
        this.width = this.datas[0].length;

        this.tileHeight = 64;
        this.tileWidth = 64;

        this.spriteSrc = spriteSrc;
        this.nbTilesInLine = 5;

        this.tileset = $("<img />", { src:this.spriteSrc, height: this.tileHeight, width: this.tileWidth})[0];
        
        this.scale = 1;

        this.rerenderall = this.rerenderall.bind(this);
        this.load = this.load.bind(this);

        this.getWidth = this.getWidth.bind(this);
        this.getHeight = this.getHeight.bind(this);
    }

    rerenderall() {

        for(let i = 0; i < this.width; i++) {
            for(let j = 0; j < this.height; j++) {
                let tile = parseInt(this.datas[i][j]);

                this.c.drawImage(
                    this.tileset,

                    (tile % this.nbTilesInLine) * this.tileWidth,
                    ((tile - tile%this.nbTilesInLine) / this.nbTilesInLine) * this.tileHeight,

                    this.tileWidth,
                    this.tileHeight,

                    this.tileWidth * j,
                    this.tileHeight * i,

                    this.tileWidth * this.scale,
                    this.tileHeight * this.scale);
            }
        }
    }

    load(datas) {
        let ret = false;

        this.datas = datas;
        let newHeight = this.datas.length;
        let newWidth = this.datas[0].length;

        if(this.width !== newWidth || this.height !== newHeight)
        {
            ret = true;
        }

        this.height = newHeight;
        this.width = newWidth;

        return ret;
    }

    getWidth() {
        return this.width * this.tileWidth;
    }
    getHeight() {
        return this.height * this.tileHeight;
    }
}

export {MapView};