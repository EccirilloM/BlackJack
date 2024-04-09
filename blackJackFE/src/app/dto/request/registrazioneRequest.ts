import { Ruolo } from "src/app/types/ruolo";

export interface RegisterRequest {
    nome: string;
    cognome: string;
    email: string;
    username: string;
    password: string;
    passwordRipetuta: string;
    dataNascita: Date;
    ruolo: Ruolo;
}