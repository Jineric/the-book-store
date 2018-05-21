import { Component, OnInit } from '@angular/core';
import {ActivatedRoute, Router} from '@angular/router';
import {Book} from '../service/model/Book';
import {BookApi} from '../service/api/BookApi';
import 'rxjs/Rx';

@Component({
  selector: 'bs-book-detail',
  templateUrl: './book-detail.component.html',
  styles: []
})
export class BookDetailComponent implements OnInit {

  private book: Book;

  constructor(private router: Router, private route: ActivatedRoute, private bookApi: BookApi) {
    this.book = new Book();
  }

  ngOnInit() {
    this.route.params
      .map(params => params['bookId'])
      .switchMap(id => this.bookApi.getBook(id))
      .subscribe(book => this.book = book);
  }

  delete() {
    this.bookApi.deleteBook(this.book.id)
      .finally(() => this.router.navigate(['/book-list']))
      .subscribe();
  }
}
