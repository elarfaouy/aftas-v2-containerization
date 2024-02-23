import {Component, OnDestroy, OnInit, ViewChild} from '@angular/core';
import {Subscription} from "rxjs";
import {MatTableDataSource} from "@angular/material/table";
import {MatPaginator} from "@angular/material/paginator";
import {MatSort, Sort} from "@angular/material/sort";
import {MemberElement} from "../../models/member-element";
import {LiveAnnouncer} from "@angular/cdk/a11y";
import {MemberService} from "../../../services/member/member.service";
import {BottomSheetMemberComponent} from "../bottom-sheet-member/bottom-sheet-member.component";
import {MatBottomSheet} from "@angular/material/bottom-sheet";
import {BottomSheetRegisterComponent} from "../bottom-sheet-register/bottom-sheet-register.component";
import {AuthenticationService} from "../../../services/authentication/authentication.service";
import {BottomSheetUpdateMemberComponent} from "../bottom-sheet-update-member/bottom-sheet-update-member.component";

@Component({
  selector: 'app-table',
  templateUrl: './table.component.html',
  styleUrl: './table.component.css'
})
export class TableComponent implements OnInit, OnDestroy {
  private _sub!: Subscription;
  displayedColumns: string[] = ["num", "name", "family name", "accession date", "nationality", "identity number", "identity document", "actions"];
  members: MemberElement[] = [];
  dataSource = new MatTableDataSource<MemberElement>(this.members);
  @ViewChild(MatPaginator) paginator!: MatPaginator;
  @ViewChild(MatSort) sort!: MatSort;

  constructor(private _memberService: MemberService,
              private _bottomSheet: MatBottomSheet,
              private _liveAnnouncer: LiveAnnouncer,
              private authService: AuthenticationService) {
  }

  ngOnInit(): void {
    this._sub = this._memberService.getMembers().subscribe(
      data => {
        this.members = data;
        this.dataSource = new MatTableDataSource<MemberElement>(this.members);
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

  openMemberBottomSheet(): void {
    this.authService.hasRightAuthority("WRITE_MEMBER").subscribe(
      (hasRight) => {
        if (hasRight) {
          this._bottomSheet.open(BottomSheetMemberComponent);
        }
      }
    );
  }

  openRegisterBottomSheet(): void {
    this.authService.hasRightAuthority("WRITE_RANKING").subscribe(
      (hasRight) => {
        if (hasRight) {
          this._bottomSheet.open(BottomSheetRegisterComponent);
        }
      }
    );
  }

  openUpdateMemberBottomSheet(user: any): void {
    this.authService.hasRightAuthority("WRITE_MEMBER").subscribe(
      (hasRight) => {
        if (hasRight) {
          this._bottomSheet.open(BottomSheetUpdateMemberComponent, {
            data: { user: user },
          });
        }
      }
    );
  }
}
