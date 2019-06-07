import { Injectable } from '@angular/core';
import { Observable } from 'rxjs';
import { HttpClient } from '@angular/common/http';
import { Cliente } from './cliente-model';

@Injectable({
   providedIn: 'root'
})
export class ClienteService {

   constructor(private _http: HttpClient) { }

   public listar(): Observable<Cliente[]> {
      return this._http
         .get<Cliente[]>("http://localhost:8080/clientes");
   }

   public save(cliente: Cliente): Observable<any> {
      return this._http
         .post("http://localhost:8080/clientes", cliente);
   }

   public delete(id: number): Observable<any> {
      return this._http
         .delete("http://localhost:8080/clientes/" + id);
   }

   public getRelatorio(): Observable<any> {
      return this._http
         .get("http://localhost:8080/clientes/report", { responseType: "blob" });
   }

}
