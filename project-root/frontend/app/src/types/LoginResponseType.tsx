import {AppUserType} from "./AppUserType.tsx";

export default interface LoginResponseType {
    appUser: AppUserType | null;
    errorMessage: string;
    token: string;
}