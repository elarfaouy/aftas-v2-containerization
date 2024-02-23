import { NgModule } from '@angular/core';
import { CommonModule } from '@angular/common';

import { MemberRoutingModule } from './member-routing.module';
import { MemberComponent } from './components/member/member.component';
import { TableComponent } from './components/table/table.component';
import {MatTableModule} from "@angular/material/table";
import {MatSortModule} from "@angular/material/sort";
import {MatPaginatorModule} from "@angular/material/paginator";
import {HttpClientModule} from "@angular/common/http";
import {RouterOutlet} from "@angular/router";
import {MatButtonModule} from "@angular/material/button";
import { BottomSheetMemberComponent } from './components/bottom-sheet-member/bottom-sheet-member.component';
import {MatFormFieldModule} from "@angular/material/form-field";
import {MatInputModule} from "@angular/material/input";
import {ReactiveFormsModule} from "@angular/forms";
import {MatSelectModule} from "@angular/material/select";
import { BottomSheetRegisterComponent } from './components/bottom-sheet-register/bottom-sheet-register.component';
import {MatAutocompleteModule} from "@angular/material/autocomplete";
import { BottomSheetUpdateMemberComponent } from './components/bottom-sheet-update-member/bottom-sheet-update-member.component';
import {MatIconModule} from "@angular/material/icon";


@NgModule({
  declarations: [
    MemberComponent,
    TableComponent,
    BottomSheetMemberComponent,
    BottomSheetRegisterComponent,
    BottomSheetUpdateMemberComponent
  ],
    imports: [
        CommonModule,
        MemberRoutingModule,
        HttpClientModule,
        RouterOutlet,
        MatTableModule,
        MatSortModule,
        MatPaginatorModule,
        MatButtonModule,
        MatFormFieldModule,
        MatInputModule,
        ReactiveFormsModule,
        MatSelectModule,
        MatAutocompleteModule,
        MatIconModule
    ]
})
export class MemberModule { }
