class MapManager {
    
    constructor(w, h) {
        
        this.fullJson = null;
        this.maps = [];
        
        this.w = w;
        this.h = h;

        this.generateFullJson = this.generateFullJson.bind(this);
    }
    
    generateFullJson(json) {
        
        this.fullJson = json;
        
        this.fullJson.layers = [];
    }
    
    getFullJson() {
        
        return this.fullJson;
    }
    
    load(name) {
        return $.ajax(
            {
                url: "./data/mapSqueletton.json"
            }
        ).done($.proxy(this.generateFullJson, this));
     }
    
    addMap(data, name, opacity) {
        
        this.maps.push(new Map(data, this.w, this.h, name, opacity, 0, 0));
    }
}

export {MapManager};