import {Injectable} from '@angular/core';
import {HttpOperation} from './http-operation';
import {MemberToDisplay} from '../models/member/member-to-display';
import {MemberToAdd} from '../models/member/member-to-add';
import {HttpClient} from '@angular/common/http';

@Injectable({
  providedIn: 'root'
})
export class ResponsibleMembersService extends HttpOperation<MemberToDisplay, MemberToAdd> {

  constructor(private http: HttpClient) {
    super('api/responsibleMembers', 'api/responsibleMembers', 'api/responsibleMembers', 'api/responsibleMembers', http);
  }
}
