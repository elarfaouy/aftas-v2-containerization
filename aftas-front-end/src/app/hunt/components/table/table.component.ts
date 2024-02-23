import {Component, OnDestroy, OnInit, ViewChild} from '@angular/core';
import {Subscription} from "rxjs";
import {CompetitionElement} from "../../../competition/models/competition-element";
import {MatTableDataSource} from "@angular/material/table";
import {MatPaginator} from "@angular/material/paginator";
import {MatSort, Sort} from "@angular/material/sort";
import {CompetitionService} from "../../../services/competition/competition.service";
import {LiveAnnouncer} from "@angular/cdk/a11y";

@Component({
  selector: 'app-table',
  templateUrl: './table.component.html',
  styleUrl: './table.component.css'
})
export class TableComponent implements OnInit, OnDestroy{
  private _sub!: Subscription;
  displayedColumns: string[] = ["code", "date", "start time", "end time", "location", "amount", "status"];
  currentCompetition?: CompetitionElement;
  nextCompetition?: CompetitionElement;
  competitions: CompetitionElement[] = [];
  dataSource = new MatTableDataSource<CompetitionElement>(this.competitions);
  @ViewChild(MatPaginator) paginator!: MatPaginator;
  @ViewChild(MatSort) sort!: MatSort;

  constructor(private _competitionService: CompetitionService,
              private _liveAnnouncer: LiveAnnouncer) {
  }
  ngOnInit(): void {
    this._sub = this._competitionService.getCompetitions().subscribe(
      data => {
        this.competitions = data.content;
        this.dataSource = new MatTableDataSource<CompetitionElement>(this.competitions.filter(value => value.status == "COMPLETED"));
        this.currentCompetition = this.competitions.find(value => value.status == "ONGOING");
        this.nextCompetition = this.competitions.filter(competition => competition.status === 'UPCOMING').sort((a, b) =>
          new Date(a.date) < new Date(b.date) ? -1 : new Date(a.date) > new Date(b.date) ? 1 : 0
        ).shift();
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
}
