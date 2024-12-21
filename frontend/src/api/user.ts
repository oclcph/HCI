import axios from "axios";
import {USER_MODULE} from "./api";

type LoginInfo = {
    phone: string,
    password: string
}

type RegisterInfo = {
    phone: string,
    password: string,
    confirmPassword: string
}

export const getUser = () =>{
    return axios.get(USER_MODULE);
}

export const loginApi = (loginInfo: LoginInfo) => {
    return axios.post(`${USER_MODULE}/login`, null, {params: loginInfo})
        .then(res => {
            return res
        })
}

export const registerApi = (register: RegisterInfo) => {
    return axios.post(`${USER_MODULE}/register`, register)
        .then(res => {
            return res
        })
}