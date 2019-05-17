import { Component, OnInit, Input, Output, EventEmitter } from '@angular/core';

@Component({
   selector: 'app-input-aula',
   templateUrl: './input-aula.component.html',
   styleUrls: ['./input-aula.component.css']
})
export class InputAulaComponent implements OnInit {

   constructor() { }

   @Output('textoChange') textoChange: EventEmitter<string> = new EventEmitter<string>();
   @Input('texto') texto: string;

   onDataChange() {
      this.textoChange.emit(this.texto);
   }

   ngOnInit() {
   }

}
