import { Component, OnInit } from '@angular/core';
import {Router} from '@angular/router';
import {Book} from '../service/model/Book';
import {BookApi} from '../service/api/BookApi';
import 'rxjs/Rx';

@Component({
  selector: 'bs-book-form',
  templateUrl: './book-form.component.html',
  styles: []
})
export class BookFormComponent implements OnInit {

  private book: Book = new Book();

  constructor(private router: Router, private bookApi: BookApi) { }

  ngOnInit() {
  }

  create() {
    this.bookApi.createBook(this.book)
      .finally(() => this.router.navigate(['/book-list']))
      .subscribe();
  }
}
