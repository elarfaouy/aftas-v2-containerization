import {Component, EventEmitter, Input, OnDestroy, OnInit, ViewChild} from '@angular/core';
import {CompetitionElement} from "../../models/competition-element";
import {Subscription} from "rxjs";
import {CompetitionService} from "../../../services/competition/competition.service";
import {MatTableDataSource} from "@angular/material/table";
import {MatPaginator} from "@angular/material/paginator";
import {LiveAnnouncer} from "@angular/cdk/a11y";
import {MatSort, Sort} from "@angular/material/sort";
import {MatBottomSheet} from "@angular/material/bottom-sheet";
import {BottomSheetComponent} from "../bottom-sheet/bottom-sheet.component";
import {AuthenticationService} from "../../../services/authentication/authentication.service";

@Component({
  selector: 'app-table',
  templateUrl: './table.component.html',
  styleUrl: './table.component.css'
})
export class TableComponent implements OnInit, OnDestroy {
  private _sub!: Subscription;
  statusFilter: string = "";
  displayedColumns: string[] = ["code", "date", "start time", "end time", "location", "amount", "status"];
  competitions: CompetitionElement[] = [];
  dataSource = new MatTableDataSource<CompetitionElement>(this.competitions);
  @ViewChild(MatPaginator) paginator!: MatPaginator;
  @ViewChild(MatSort) sort!: MatSort;

  constructor(private _competitionService: CompetitionService,
              private _bottomSheet: MatBottomSheet,
              private _liveAnnouncer: LiveAnnouncer,
              private authService: AuthenticationService) {
  }

  ngOnInit(): void {
    this._sub = this._competitionService.getCompetitions().subscribe(
      data => {
        this.competitions = data.content;
        this.dataSource = new MatTableDataSource<CompetitionElement>(this.competitions);
        this.dataSource.paginator = this.paginator;
        this.dataSource.sort = this.sort;
      },
      error => console.error(error)
    )
  }

  ngOnDestroy(): void {
    this._sub.unsubscribe();
  }

  announceSortChange(sortState: Sort) {
    // This example uses English messages. If your application supports
    // multiple language, you would internationalize these strings.
    // Furthermore, you can customize the message to add additional
    // details about the values being sorted.
    if (sortState.direction) {
      this._liveAnnouncer.announce(`Sorted ${sortState.direction}ending`);
    } else {
      this._liveAnnouncer.announce('Sorting cleared');
    }
  }

  filterData(selectedStatus: string): void {
    if (selectedStatus === "") {
      this.dataSource = new MatTableDataSource<CompetitionElement>(this.competitions);
    } else {
      this.dataSource = new MatTableDataSource<CompetitionElement>(this.competitions.filter(item => item.status === selectedStatus));
    }
    this.dataSource.paginator = this.paginator;
    this.dataSource.sort = this.sort;
  }

  openBottomSheet(): void {
    this.authService.hasRightAuthority("WRITE_COMPETITION").subscribe(
      (hasRight) => {
        if (hasRight) {
          this._bottomSheet.open(BottomSheetComponent);
        }
      }
    );
  }
}
