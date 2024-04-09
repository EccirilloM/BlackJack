import { Ruolo } from "src/app/types/ruolo";

export interface RegistrazioneRequest {
    nome: string;
    cognome: string;
    email: string;
    username: string;
    password: string;
    passwordRipetuta: string;
    dataNascita: Date;
}