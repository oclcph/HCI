import { defineStore } from 'pinia';

export interface User {
    name: string;
}

export const useUserStore = defineStore('user', {
    state: () => ({
        user: null as User | null,
        isLoggedIn: false,
    }),
    actions: {
        login(userInfo: User) {
            this.user = userInfo;
            this.isLoggedIn = true;
        },
        logout() {
            this.user = null;
            this.isLoggedIn = false;
        },
    },
});
