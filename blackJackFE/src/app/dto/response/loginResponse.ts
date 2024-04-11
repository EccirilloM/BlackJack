export interface LoginResponse {
    userId: number;
    nome: string;
    cognome: string;
    email: string;
    username: string;
    ruolo: string;
    message: string;
    jwt: string;
    saldo: number;
    dataNascita: string;
    dataRegistrazione: string;
}