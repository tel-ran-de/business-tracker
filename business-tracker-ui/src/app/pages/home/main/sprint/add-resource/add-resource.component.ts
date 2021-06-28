import {Component, EventEmitter, OnInit, Output} from '@angular/core';
import {ResourceToAdd} from '../../../../../models/resource/resource-to-add';
import {FormBuilder, FormGroup, Validators} from '@angular/forms';
import {ResourceService} from '../../../../../serivce/resource.service';

@Component({
  selector: 'app-add-resource',
  templateUrl: './add-resource.component.html',
  styleUrls: ['./add-resource.component.css']
})
export class AddResourceComponent implements OnInit {

  form: FormGroup;

  @Output()
  formClosed: EventEmitter<void> = new EventEmitter<void>();

  constructor(private fb: FormBuilder, private resourceService: ResourceService) {
  }

  ngOnInit(): void {
    this.form = this.fb.group({name: ['', Validators.required], money: [''], time: ['']});
  }

  onSubmit(): void {
    const resToAdd: ResourceToAdd = new ResourceToAdd();
    resToAdd.name = this.form.controls.name.value;
    resToAdd.hours = this.form.controls.time.value;
    resToAdd.cost = this.form.controls.money.value;
    this.resourceService.transferAddedResource(resToAdd);
    this.formClosed.emit();
  }
}
