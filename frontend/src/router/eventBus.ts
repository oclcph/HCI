import { reactive } from 'vue';

const eventBus = reactive({
    footerVisible: false,
    setFooterVisible(value: boolean) {
        this.footerVisible = value;
    },
});

export default eventBus;
