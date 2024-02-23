import { NgModule } from '@angular/core';
import { CommonModule } from '@angular/common';

import { HuntRoutingModule } from './hunt-routing.module';
import { HuntComponent } from './components/hunt/hunt.component';
import { TableComponent } from './components/table/table.component';
import {MatTableModule} from "@angular/material/table";
import {MatSortModule} from "@angular/material/sort";
import {MatPaginatorModule} from "@angular/material/paginator";
import {MatCardModule} from "@angular/material/card";
import { CompetitionComponent } from './components/competition/competition.component';
import {MatExpansionModule} from "@angular/material/expansion";
import { BottomSheetHuntComponent } from './components/bottom-sheet-hunt/bottom-sheet-hunt.component';
import {MatButtonModule} from "@angular/material/button";
import {MatFormFieldModule} from "@angular/material/form-field";
import {MatInputModule} from "@angular/material/input";
import {MatAutocompleteModule} from "@angular/material/autocomplete";
import {FormsModule, ReactiveFormsModule} from "@angular/forms";


@NgModule({
  declarations: [
    HuntComponent,
    TableComponent,
    CompetitionComponent,
    BottomSheetHuntComponent
  ],
  imports: [
    CommonModule,
    HuntRoutingModule,
    FormsModule,
    ReactiveFormsModule,
    MatTableModule,
    MatSortModule,
    MatPaginatorModule,
    MatCardModule,
    MatExpansionModule,
    MatButtonModule,
    MatFormFieldModule,
    MatInputModule,
    MatAutocompleteModule
  ]
})
export class HuntModule { }
