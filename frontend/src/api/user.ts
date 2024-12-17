import axios from "axios";
import {USER_MODULE} from "./api";

export const getUser = () =>{
    return axios.get(USER_MODULE);
}