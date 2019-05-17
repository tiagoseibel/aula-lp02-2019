import { Component, OnInit } from '@angular/core';
import { ClienteService } from '../cliente.service';
import { Cliente } from '../cliente-model';
import { FormGroup, FormControl, Validators } from '@angular/forms';

@Component({
   selector: 'app-index',
   templateUrl: './index.component.html'
})
export class IndexComponent implements OnInit {

   constructor(private _clienteService: ClienteService) { }

   lista: Cliente[];

   myform: FormGroup;

   teste: string;

   ngOnInit() {

      this.load();

      this.myform = new FormGroup(
         {
            codigo: new FormControl(),
            nome: new FormControl()
         }
      );

   }

   public save(): void {
      const cli = 
         Object.assign({}, this.myform.value) as Cliente;

      this._clienteService.save(cli).subscribe(
         (result) => {
            this.load();
         },
         (erro) => {
            console.log('erro: ' + erro.message);
         }, () => {
            console.log('sempre');

         }
      );

      this._clienteService.listar().subscribe(
         (result) => { this.lista = result }
      );
   }


   public load() {
      this._clienteService.listar().subscribe(
         (result) => { this.lista = result }
      );       
   }

   public delete(cli?: Cliente) {
      this._clienteService.delete(cli.codigo).subscribe(
         (resultado) => {
            console.log('OK');
           this.load();
         }, () => {

         }
      );

   }

   public mostraRelatorio(): void {
      this._clienteService.getRelatorio().subscribe(
         (resultado) => {
            const url = window.URL.createObjectURL(resultado);
            window.open(url);
         }
      );
   }

}
