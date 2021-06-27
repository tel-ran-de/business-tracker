import {Injectable} from '@angular/core';
import {HttpClient, HttpHeaders} from '@angular/common/http';
import {MemberToDisplay} from '../models/member/member-to-display';
import {Observable} from "rxjs";

@Injectable({
  providedIn: 'root'
})
export class MemberService {

  options = {
    headers: new HttpHeaders({'Content-Type': 'application/json'})
  };

  constructor(private httpClient: HttpClient) {
  }

  getAllMembersByProjectId(projectId: number): Observable<MemberToDisplay[]> {
    const url = `api/members/project/${projectId}`
    return this.httpClient.get<MemberToDisplay[]>(url, this.options);
  }

  getMemberById(memberId: number): Observable<MemberToDisplay> {
    const url = `api/members/${memberId}`
    return this.httpClient.get<MemberToDisplay>(url, this.options);
  }
}
