import { reactive } from 'vue';

const eventBus = reactive({
    fixedFooterVisible: false,
    relativeFooterVisible: false,
    setFixedFooterVisible(value: boolean) {
        this.fixedFooterVisible = value;
    },
    setRelativeFooterVisible(value: boolean) {
        this.relativeFooterVisible = value;
    }
});

export default eventBus;
