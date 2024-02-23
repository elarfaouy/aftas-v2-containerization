import { NgModule } from '@angular/core';
import { CommonModule } from '@angular/common';
import {CompetitionRoutingModule} from "./competition-routing.module";
import {MatTableModule} from "@angular/material/table";
import {MatPaginatorModule} from "@angular/material/paginator";
import {CompetitionComponent} from "./components/competition/competition.component";
import {RouterOutlet} from "@angular/router";
import {MatSortModule} from "@angular/material/sort";
import { TableComponent } from './components/table/table.component';
import {HttpClientModule} from "@angular/common/http";
import {MatButtonModule} from "@angular/material/button";
import { BottomSheetComponent } from './components/bottom-sheet/bottom-sheet.component';
import {MatFormFieldModule} from "@angular/material/form-field";
import {MatBottomSheetModule} from "@angular/material/bottom-sheet";
import {FormsModule, ReactiveFormsModule} from "@angular/forms";
import {MatInputModule} from "@angular/material/input";
import {MatRadioModule} from "@angular/material/radio";
import { PodiumComponent } from './components/podium/podium.component';
import {MatCardModule} from "@angular/material/card";

@NgModule({
  declarations: [
    CompetitionComponent,
    TableComponent,
    BottomSheetComponent,
    PodiumComponent,
  ],
    imports: [
        CommonModule,
        CompetitionRoutingModule,
        HttpClientModule,
        RouterOutlet,
        MatTableModule,
        MatPaginatorModule,
        MatButtonModule,
        MatSortModule,
        MatBottomSheetModule,
        MatFormFieldModule,
        FormsModule,
        ReactiveFormsModule,
        MatInputModule,
        MatRadioModule,
        MatCardModule
    ],
})
export class CompetitionModule { }
