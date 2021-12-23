class Test {

    /**
     *
     * @param tokenLimit
     */
    constructor(tokenLimit) {
        this.tokenLimit = tokenLimit;
        this.initToken = 0;
        this.time_S = 0;
        this.book = new Array();
    }

    /**
     *
     * @param ruleId
     * @param time
     * @param interval
     * @param number
     */
    addRule(ruleId, time, interval, number) {
        if (this.book[ruleId] == true) {
            return false;
        }
        this.ruleId = ruleId;
        this.time = time;
        this.interval = interval;
        this.number = number;
        this.book[ruleId] = true;
        return true;
    }

    /**
     *
     * @param ruleId
     * @param time
     */
    removeRule(ruleId, time) {
        if (!this.book[ruleId]) {
            return false;
        } else if (this.book[ruleId] == true) {
            if (this.time == time) {
                this.tokenLimit = (time - this.time) / this.interval * this.number;
                this.time = time;
            }
        }
    }

    /**
     *
     * @param time
     * @param size
     */
    transferData(time, size) {
        if (size > (time - this.time) / this.interval * this.number) {
            return false;
        } else {
            this.tokenLimit -= size;
            t
        }
        return true;
    }

    /**
     *
     * @param time
     */
    queryToken(time) {
        this.time = time;
        return this.tokenLimit;
    }

}