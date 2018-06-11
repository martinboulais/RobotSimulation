class Tempo {

    constructor(basePeriod, outputPeriod) {

        //The base period is limited to one
        this.basePeriod = Math.max(basePeriod, 1);

        //The output period is longer than base
        this.outputPeriod = Math.max(outputPeriod, this.basePeriod);

        this.reloadValue = parseInt(this.outputPeriod / this.basePeriod);

        this.counter = 0;

        this.refresh = this.refresh.bind(this);
    }

    refresh() {

        this.counter = (this.counter + 1) % this.reloadValue;

        return this.counter === 0;
    }
}

export {Tempo};