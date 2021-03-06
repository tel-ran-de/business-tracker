import {Component, OnDestroy, OnInit} from '@angular/core';
import {FormBuilder, FormGroup, Validators} from '@angular/forms';
import {MemberToDisplay} from '../../../../../models/member/member-to-display';
import {TaskService} from '../../../../../serivce/task.service';
import {ResourceService} from '../../../../../serivce/resource.service';
import {MemberService} from '../../../../../serivce/member.service';
import {TaskToAdd} from '../../../../../models/task/task-to-add';
import {ActivatedRoute, Router} from '@angular/router';
import {ResourceToAdd} from '../../../../../models/resource/resource-to-add';
import {Observable, Subscription} from 'rxjs';


@Component({
  selector: 'app-add-task',
  templateUrl: './add-task.component.html',
  styleUrls: ['./add-task.component.css']
})
export class AddTaskComponent implements OnInit, OnDestroy {
  form: FormGroup;

  membersToSelect: MemberToDisplay;
  private resources: ResourceToAdd[] = [];

  membersList: Observable<MemberToDisplay[]>;
  subscriptions: Subscription[] = [];
  deliveries: string[] = ['Document', 'Presentation', "Nothing"];

  active = false;
  finished = false;

  constructor(private fb: FormBuilder,
              private route: ActivatedRoute,
              private taskService: TaskService,
              private resourceService: ResourceService,
              public memberService: MemberService,
              private router: Router) {
  }


  ngOnInit(): void {
    this.initForm();
    const projectId = +this.route.snapshot.paramMap.get("projectId");
    this.membersList = this.memberService.getAllMembersByProjectId(projectId);
    this.subscribeResourceTriggers();
  }

  onSubmit(): void {

    const taskToAdd: TaskToAdd = new TaskToAdd();
    taskToAdd.name = this.form.controls.name.value;
    taskToAdd.mileStoneId = +this.route.snapshot.paramMap.get('mileStoneId');
    taskToAdd.delivery = this.form.controls.delivery.value;
    // taskToAdd.memberId = this.getAddMember().id;
    taskToAdd.memberId = this.form.controls.member.value.id;

    taskToAdd.active = this.active;
    taskToAdd.finished = this.finished;
    taskToAdd.resources = this.resources ? this.resources : [];

    const addTaskSub = this.taskService.addTask(taskToAdd).subscribe(() => this.navigateToTaskPage(),
      error => console.error(error));

    this.subscriptions.push(addTaskSub);
  }

  navigateToTaskPage(): void {
    this.router.navigate(['../../..'], {relativeTo: this.route});
  }

  private initForm() {
    this.form = this.fb.group({
      name: ['', Validators.required],
      member: ['', Validators.required],
      delivery: ['', Validators.required]
    });
  }

  ngOnDestroy(): void {
    for (const subscription of this.subscriptions) {
      if (subscription) {
        subscription.unsubscribe();
      }
    }
  }

  private subscribeResourceTriggers() {
    const transferSubscribe = this.resourceService.addResourceTransfer$
      .subscribe(value => {
        this.resources.push(value);
        this.resourceService.reloadAddedResourceList$.next(this.resources);
      });

    const resRemovedSub = this.resourceService.reloadResourceListRemoved$
      .subscribe(value => {
        const removedResIndex = this.resources.indexOf(value);
        this.resources.splice(removedResIndex, 1);
      });

    this.subscriptions.push(transferSubscribe, resRemovedSub);
  }

  getDate(): string {
    return new Date().toISOString().split("T")[0];
  }
}
