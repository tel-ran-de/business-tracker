import {Injectable} from '@angular/core';
import {HttpOperation} from './http-operation';
import {HttpClient} from '@angular/common/http';
import {MemberToDisplay} from '../models/member/member-to-display';
import {MemberToAdd} from '../models/member/member-to-add';

@Injectable({
  providedIn: 'root'
})
export class MemberService extends HttpOperation<MemberToDisplay, MemberToAdd> {

  constructor(private http: HttpClient) {
    super('api/member', 'api/member', 'api/member', 'api/member', http);
  }
}
